package springboot.policy_module.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springboot.policy_module.controllers.dto.UserRegistrationDto;
import springboot.policy_module.models.Policy;
import springboot.policy_module.models.User;
import springboot.policy_module.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto,
                                      BindingResult result) {

        User existing = userService.findByUsername(registrationDto.getUsername());
        if (existing != null) {
            result.rejectValue("username", null, "There is Already an Account Registered With That Username !");
        }

        if (result.hasErrors()) {
            return "registration";
        }

        userService.save(registrationDto);

        return "redirect:/login";
    }

}
