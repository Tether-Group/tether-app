package tethergroup.tether.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.UserRepository;

@Controller
public class UserController {

    private final UserRepository userDao;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    //    creating user
    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "users/login";
    }

    //    creating user
    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }


    // viewing profile when logged in
    @GetMapping("/profile/{username}")
    public String returnProfilePage(Model model, @PathVariable String username) {
        User user = userDao.findByUsername(username);
        model.addAttribute("user", user);
        return "users/profile";
    }



    //    viewing friends list
    @GetMapping("/friends")
    public String returnFriendsListPage() {
        return "users/friends";
    }


    //   view settings page
    @GetMapping("/profile/settings")
    public String returnSettingsPage(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        model.addAttribute("user", user);
        return "users/edit-user";
    }



    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute User user, HttpSession session) {
        String userPassword = userDao.findById(user.getId()).get().getPassword();
        user.setPassword(userPassword);
        userDao.save(user);
        return "redirect:/";
    }
}