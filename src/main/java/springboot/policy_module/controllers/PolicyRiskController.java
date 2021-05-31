package springboot.policy_module.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboot.policy_module.models.Policy;
import springboot.policy_module.models.PolicyRisk;
import springboot.policy_module.models.Risk;
import springboot.policy_module.services.PolicyRiskService;
import springboot.policy_module.services.RiskService;

import java.util.List;

@Controller
@RequestMapping("/policyRisk")
public class PolicyRiskController {
    // For Adding and Removing Policy-Risk Mapping
    @Autowired
    private PolicyRiskService policyRiskService;

    // For Bringing Up Available User Created Risks to Add Mapping
    @Autowired
    private RiskService riskService;

    @GetMapping
    public String viewPolicyRiskMappingHomePage(Model model) {
        return findPaginatedMapping(1, "policyId", "asc", model);
    }

    @GetMapping("/showPolicyRiskMappingPage")
    public String showNewPolicyRiskForm(Model model) {
        return findPaginatedRisks(1, "title", "asc", model);
    }

    @GetMapping("/newPolicyRiskMapping")
    public String viewRisks(Model model) {
        return findPaginatedRisks(1, "title", "asc", model);
    }

    @GetMapping("/savePolicyRiskMapping/{policyId}/{riskId}")
    public String savePolicyRiskMapping(@PathVariable(value="riskId") Long riskId,
                                        @PathVariable(value="policyId") Long policyId) {
        // Save Risk to the Database
        PolicyRisk policyRisk = new PolicyRisk();
        policyRisk.setPolicyId(policyId);
        policyRisk.setRiskId(riskId);
        policyRisk.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        this.policyRiskService.savePolicyRiskMapping(policyRisk);
        return "redirect:/policyRisk/showPolicyRiskMappingPage";
    }

    @GetMapping("/deletePolicyRiskMapping/{policyId}/{riskId}")
    public String deletePolicyRiskMapping(@PathVariable(value="riskId") Long riskId,
                                          @PathVariable(value="policyId") Long policyId) {
        this.policyRiskService.deletePolicyRiskMappingById(policyId, riskId);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginatedMapping(@PathVariable(value="pageNo") Integer pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        Integer pageSize = 5;
        Page<PolicyRisk> page = policyRiskService.findPaginatedMapping(pageNo, pageSize, sortField, sortDir);
        List<PolicyRisk> listPolicyRisks = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listPolicyRisks", listPolicyRisks);

        return "policyRisk/policyRisk";
    }

    @GetMapping("/riskPage/{pageNo}")
    public String findPaginatedRisks(@PathVariable(value="pageNo") Integer pageNo,
                                       @RequestParam("sortField") String sortField,
                                       @RequestParam("sortDir") String sortDir,
                                       Model model) {
        Integer pageSize = 5;
        Page<Risk> page = riskService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Risk> listRisks = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listRisks", listRisks);

        return "policyRisk/newPolicyRiskMapping";
    }
}
