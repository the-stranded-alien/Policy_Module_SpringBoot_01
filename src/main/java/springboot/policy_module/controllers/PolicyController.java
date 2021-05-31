package springboot.policy_module.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springboot.policy_module.models.Policy;
import springboot.policy_module.services.PolicyRiskService;
import springboot.policy_module.services.PolicyService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/policy")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @Autowired
    private PolicyRiskService policyRiskService;

    @GetMapping
    public String viewPolicyHomePage(Model model) {
        return findPaginated(1, "policyName", "asc", model);
    }

    @GetMapping("/showNewPolicyForm")
    public String showNewPolicyForm(Model model) {
        Policy policy = new Policy();
        model.addAttribute("policy", policy);
        return "policy/newPolicy";
    }

    @PostMapping("/savePolicy")
    public String savePolicy(@ModelAttribute("policy") Policy policy, HttpServletRequest req) {
        HttpSession session = req.getSession();
        Policy savedPolicy = this.policyService.savePolicy(policy);
        session.setAttribute("policyId", savedPolicy.getId());
        return "redirect:/policyRisk";
    }

    @GetMapping("/showUpdatePolicyForm/{id}")
    public String showUpdatePolicyForm(@PathVariable(value="id") Long id, Model model) {
        Policy policy = policyService.getPolicyById(id);
        model.addAttribute("policy", policy);
        return "policy/updatePolicy";
    }

    @GetMapping("/deletePolicy/{id}")
    @Transactional
    public String deletePolicy(@PathVariable(value="id") Long id) {
        this.policyService.deletePolicyById(id);
        this.policyRiskService.deletePolicyRiskMappingByPolicyId(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value="pageNo") Integer pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        Integer pageSize = 3;
        Page<Policy> page = policyService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Policy> listPolicies = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listPolicies", listPolicies);

        return "policy/policy";
    }

}
