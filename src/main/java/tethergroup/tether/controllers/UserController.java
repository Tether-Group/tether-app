package tethergroup.tether.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.UserRepository;

@Controller
public class UserController {

    private UserRepository userDao;

    @GetMapping("/register")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        return "users/login";
    }


    @GetMapping("/profile")
    public String returnProfilePage() {return "users/profile";}

    @GetMapping("/friends")
    public String returnFriendsListPage() {return "users/friends";}
}
