package tethergroup.tether.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import tethergroup.tether.models.User;

import java.io.ObjectInputFilter;

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    public String showLoginForm(Model model, @ModelAttribute("new-user") String flashAttrRegister, @ModelAttribute("password-change-success") String flashAttrPasswordChange) {
        model.addAttribute("user", new User());
        model.addAttribute("flashAttrRegisterSuccess", flashAttrRegister);
        model.addAttribute("flashAttrPWSuccess", flashAttrPasswordChange);

        return "users/login";
    }
}