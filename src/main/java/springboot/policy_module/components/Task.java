package springboot.policy_module.components;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import springboot.policy_module.models.*;
import springboot.policy_module.services.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Task {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private RiskService riskService;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private PolicyRiskService policyRiskService;

    @Autowired
    private ActivityService activityService;

    private static final Logger log = LoggerFactory.getLogger(Task.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        log.info("Time Now : {}", dateFormat.format(new Date()));
        List<User> allUsers = userService.findAll();
        for(User currentUser : allUsers) {
            String currentUserFullName = currentUser.getFirstName() + " " + currentUser.getLastName();
            String currentUserUsername = currentUser.getUsername();
            String currentUserDirectory = currentUser.getFileDirectory();
            log.info("User's Full Name, Username & Directory : {}, {}, {}", currentUserFullName, currentUserUsername, currentUserDirectory);
            List<Policy> currentUserPolicies = policyService.getPolicyByUsername(currentUserUsername);
            for(Policy currentPolicy : currentUserPolicies) {
                Long currentPolicyId = currentPolicy.getId();
                List<PolicyRisk> currentPolicyRisks = policyRiskService.getAllPolicyRiskByPolicyId(currentPolicyId);
                for(PolicyRisk currentPolicyRiskPair : currentPolicyRisks) {
                    Long policyId = currentPolicyRiskPair.getPolicyId();
                    Long riskId = currentPolicyRiskPair.getRiskId();
                    String byUser = currentPolicyRiskPair.getUsername();
                    log.info("User & Current Policy Risk Pair {}, {}, {}", byUser, policyId, riskId);
                    Risk currentRisk = riskService.getRiskById(riskId);
                    // Now for Each User We Have :
                    // All his Policies
                    // For all his Policies we have :
                    // The Id's of Risked Map to each of his Policies
                    // Hence We also have All his Risks in Each Policy
                    // Now we will Fetch all the Files in his Directory that were uploaded in Last 'X' Hours:
                    // For each of the user's File fetched we will Test All Risks in All his Policies !!
                    try {
                    Pattern pattern = Pattern.compile(currentRisk.getRegex());
                    String keyWordString = currentRisk.getKeyWords();
                    File currentDir = new File(".");
                    File userDirectoryPath = new File(currentDir + currentUserDirectory);
                    File userFileList[] = userDirectoryPath.listFiles();

                    Scanner sc = null;

                    for(File file : userFileList) {

                        Activity currentActivity = new Activity();
                        currentActivity.setActivityLogTime(LocalDateTime.now());
                        currentActivity.setUsername(currentUserUsername);
                        currentActivity.setUserDirectory(currentUserDirectory);
                        currentActivity.setFileName(file.getName());
                        currentActivity.setPolicyId(policyId);
                        currentActivity.setRiskId(riskId);

                        Boolean resultPolicyRiskPair = true;

                        Path path = Paths.get("" + file);
                        System.out.println(path);
                        BasicFileAttributes attr;
                        try {
                            attr = Files.readAttributes(path, BasicFileAttributes.class);
                            FileTime time = attr.creationTime();

                            if((time.toMillis() - Calendar.getInstance().getTimeInMillis()) >= 60000l) {
                                System.out.println("Inside If !!");
                                log.info("Eligible : File_Name & File_Path -> {}, {}", file.getName(), file.getAbsolutePath());
                                try {
                                    sc = new Scanner(file);
                                    String input;
                                    StringBuffer sb = new StringBuffer();
                                    while(sc.hasNextLine()) {
                                        input = sc.nextLine();
                                        sb.append(input + " ");
                                    }
                                    String ss = sb.toString();
                                    String[] tokens = ss.split("[,;. \t\n\r]++");
                                    HashMap<String, Integer> hs = new HashMap<>();
                                    for(String s : tokens) {
                                        Matcher matcher = pattern.matcher(s);
                                        System.out.println(s);
                                        boolean matchFound = matcher.find();
                                        if(matchFound) {
                                            resultPolicyRiskPair = false;
                                            System.out.println("Match Found !");
                                        }
                                        if(!hs.containsKey(s)) {
                                            hs.put(s, 1);
                                        } else {
                                            hs.put(s, hs.get(s) + 1);
                                        }
                                    }
                                    String[] keyWordList = keyWordString.split(",");
                                    ArrayList<String> ar = new ArrayList<>();
                                    for(String kw : keyWordList) {
                                        ar.add(kw.trim());
                                    }
                                    for(int i = 0; i < ar.size(); i++) {
                                        if(hs.containsKey(ar.get(i))) {
                                            resultPolicyRiskPair = false;
                                            System.out.println(ar.get(i) + " Present " + hs.get(ar.get(i)) + " Times ! ");
                                            System.out.println("Found in File Named : " + file.getName());
                                        }
                                    }
                                    if(resultPolicyRiskPair) {
                                        currentActivity.setResult("Cleared");
                                        activityService.saveActivity(currentActivity);
                                        mailService.sendMail("sahil16gupta11@gmail.com","User Name : " + currentUserUsername + "\n" + "File Name : " + currentActivity.getFileName() + "\n" + "Policy Id : " + currentActivity.getPolicyId() + "\n" + "Risk Id : " + currentActivity.getRiskId() + "\n" + "Result : " + currentActivity.getResult(), "No Risk Detected");
                                    } else {
                                        currentActivity.setResult("Failed");
                                        activityService.saveActivity(currentActivity);
                                        mailService.sendMail("sahil16gupta11@gmail.com","User Name : " + currentUserUsername + "\n" + "File Name : " + currentActivity.getFileName() + "\n" + "Policy Id : " + currentActivity.getPolicyId() + "\n" + "Risk Id : " + currentActivity.getRiskId() + "\n" + "Result : " + currentActivity.getResult(), "Risk Detected");
                                    }

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("Some Error : " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
}
