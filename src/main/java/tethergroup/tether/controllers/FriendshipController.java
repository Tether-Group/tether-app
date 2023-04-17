package tethergroup.tether.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tethergroup.tether.models.Friendship;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.FriendshipRepository;
import tethergroup.tether.repositories.UserRepository;

@Controller
public class FriendshipController {

    private final UserRepository userDao;
    private final FriendshipRepository friendshipDao;

    FriendshipController(UserRepository userDao, FriendshipRepository friendshipDao) {
        this.userDao = userDao;
        this.friendshipDao = friendshipDao;
    }

    @PostMapping("/profile/{userId}/add")
    public String sendFriendRequest(@PathVariable Long userId) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User requester = userDao.findById(loggedInUser.getId()).get();
        User acceptor = userDao.findById(userId).get();
        Friendship newFriendshipRequest = new Friendship();
        newFriendshipRequest.setRequester(requester);
        newFriendshipRequest.setAcceptor(acceptor);
        newFriendshipRequest.setPending(true);

        friendshipDao.save(newFriendshipRequest);
        return "redirect:/profile/" + acceptor.getUsername();
    }

    @PostMapping("/profile/{userId}/cancel")
    public String cancelFriendRequest(@PathVariable Long userId) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User requester = userDao.findById(loggedInUser.getId()).get();
        User acceptor = userDao.findById(userId).get();
        Friendship friendRequest = friendshipDao.findByRequester_IdAndAcceptor_Id(requester.getId(), acceptor.getId());
        friendshipDao.delete(friendRequest);
        return "redirect:/profile/" + acceptor.getUsername();
    }

    @PostMapping("/profile/{userId}/remove")
    public String removeFriendRequest(@PathVariable Long userId) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User requester = userDao.findById(loggedInUser.getId()).get();
        User acceptor = userDao.findById(userId).get();
        Friendship friendRequest = friendshipDao.findByRequester_IdAndAcceptor_Id(requester.getId(), acceptor.getId());
        friendshipDao.delete(friendRequest);
        return "redirect:/profile/" + acceptor.getUsername();
    }
}
