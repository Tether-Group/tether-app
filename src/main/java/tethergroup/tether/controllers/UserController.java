package tethergroup.tether.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class UserController {

    @GetMapping("/login")
    public String returnLoginPage() {return "users/login";}


    @GetMapping("/profile")
    public String returnProfilePage() {return "users/profile";}

    @GetMapping("/friends")
    public String returnFriendsListPage() {return "users/friends";}
}
