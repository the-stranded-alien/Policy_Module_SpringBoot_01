package springboot.policy_module.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime activityLogTime; // Time of Log Generation

    // Can be Mapped to user.userDirectory
    private String username;
    private String userDirectory; // The Directory to Which File Belongs

    private String fileName; // File Name Fetched From File Meta

    private String fileCreationTime; // Creation Time (From File Meta)

    private Long policyId; // All Policies against which File is Checked
    private Long riskId; // Policies Violated by File
    private String result; // Policies Not Violated by File

    public Activity() {

    }

    public Activity(LocalDateTime activityLogTime, String username, String userDirectory, String fileName, String fileCreationTime, Long policyId, Long riskId, String result) {
        this.activityLogTime = activityLogTime;
        this.username = username;
        this.userDirectory = userDirectory;
        this.fileName = fileName;
        this.fileCreationTime = fileCreationTime;
        this.policyId = policyId;
        this.riskId = riskId;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getActivityLogTime() {
        return activityLogTime;
    }

    public void setActivityLogTime(LocalDateTime activityLogTime) {
        this.activityLogTime = activityLogTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserDirectory() {
        return userDirectory;
    }

    public void setUserDirectory(String userDirectory) {
        this.userDirectory = userDirectory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileCreationTime() {
        return fileCreationTime;
    }

    public void setFileCreationTime(String fileCreationTime) {
        this.fileCreationTime = fileCreationTime;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public Long getRiskId() {
        return riskId;
    }

    public void setRiskId(Long riskId) {
        this.riskId = riskId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        return id != null ? id.equals(activity.id) : activity.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", activityLogTime=" + activityLogTime +
                ", username='" + username + '\'' +
                ", userDirectory='" + userDirectory + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileCreationTime=" + fileCreationTime +
                ", policyId=" + policyId +
                ", riskId=" + riskId +
                ", result='" + result + '\'' +
                '}';
    }
}
