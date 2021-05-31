package springboot.policy_module.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.policy_module.models.Activity;
import springboot.policy_module.services.ActivityService;

import java.util.List;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public String showActivityPage(Model model) {
        return findPaginated(SecurityContextHolder.getContext().getAuthentication().getName(), 1, "id", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(String username, @PathVariable(value = "pageNo") Integer pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {

        Integer pageSize = 10;
        Page<Activity> page = activityService.findPaginated(username, pageNo, pageSize, sortField, sortDir);
        List<Activity> listActivity = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listActivity", listActivity);

        return "activity/activity";
    }
}
