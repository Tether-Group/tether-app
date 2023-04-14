package tethergroup.tether.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.UserRepository;

import java.util.Optional;

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
        if (user == null) {
            return "redirect:/error";
        }
        model.addAttribute("user", user);
        return "users/profile";
    }

    // my profile tab from navbar
    @GetMapping("/profile/my-account")
    public String returnLoggedInUserProfilePage(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> actualUser = userDao.findById(user.getId());
        if (actualUser.isPresent()) {
            User userObj = actualUser.get();
            model.addAttribute("user", userObj);
        } else {
            return "redirect:/login";
        }
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
        Optional<User> actualUser = userDao.findById(user.getId());
        if (actualUser.isPresent()) {
            User userObj = actualUser.get();
            model.addAttribute("user", userObj);
        } else {
            return "redirect:/login";
        }
        return "users/edit-user";
    }



    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute User user, HttpSession session) {
        System.out.println(user.getId());
        String userPassword = userDao.findById(user.getId()).get().getPassword();
        user.setPassword(userPassword);
        userDao.save(user);
        return "redirect:/";
    }

    @PostMapping("/profile/editpassword")
    public String updatePassword(@RequestParam ("oldpassword") String oldPassword, @RequestParam ("newpassword") String newPassword){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String oldPasswordFromDataBase = userDao.findById(loggedInUser.getId()).get().getPassword();
        boolean doesMatch = passwordEncoder.matches(oldPassword, oldPasswordFromDataBase);

        if (!doesMatch) {
            return "redirect:/profile/settings";
        } else {
            User user = userDao.findById(loggedInUser.getId()).get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userDao.save(user);
            return "redirect:/";
        }
    }



    @PostMapping("/profile/delete")
    public String deleteAccount() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User actualUser = userDao.findById(loggedInUser.getId()).get();
        userDao.delete(actualUser);
        SecurityContextHolder.clearContext();
        return "redirect:/my/logout";
    }


    @GetMapping("/my/logout")
    public String manualLogout(HttpServletRequest request) {
        try {
            request.logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }
}