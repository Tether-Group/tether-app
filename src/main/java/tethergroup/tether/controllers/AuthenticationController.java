package tethergroup.tether.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tethergroup.tether.models.User;

import java.io.ObjectInputFilter;

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "users/login";
    }

//    @Value("${talkjs.appkey}")
//    private String talkJSAppKey;

//    @GetMapping(value = "/keys.js", produces = "text/javascript")
//    @ResponseBody
//    public String getKeys() {
//       return "CONST TALK_JS_KEY = " +  + ";";
//    }
}