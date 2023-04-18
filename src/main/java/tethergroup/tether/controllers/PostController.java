package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tethergroup.tether.models.Post;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.GroupRepository;
import tethergroup.tether.repositories.PostRepository;
import tethergroup.tether.repositories.PostTypeRepository;
import tethergroup.tether.repositories.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;
    private final GroupRepository groupDao;
    private final PostTypeRepository postTypeDao;


//    @GetMapping("/post/create")
//    public String createPost(Model model){
//        model.addAttribute("post", new Post());
//        return "posts/create-post";
//    }


    @PostMapping("/post/create/text")
    public String createTextPost(
                             @RequestParam("header") String title,
                             @RequestParam("body") String body,
                             @RequestParam("id") Long groupId) {
        Post post = new Post();
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(loggedInUser);
        post.setGroup(groupDao.findById(groupId).get());
        post.setHeader(title);
        post.setBody(body);
        post.setPostType(postTypeDao.findById(1L).get());
        Timestamp timestamp = new Timestamp((new Date()).getTime());
        post.setPostDate(timestamp);
        postDao.save(post);
        return "redirect:/";
    }


    @PostMapping("/post/create/event")
    public String createEventPost(
            @RequestParam("header") String title,
            @RequestParam("date") LocalDate dateString,
            @RequestParam("address") String address,
            @RequestParam("body") String body,
            @RequestParam("id") Long groupId) {
        Post post = new Post();
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(loggedInUser);
        post.setGroup(groupDao.findById(groupId).get());
        post.setHeader(title);
        post.setEventAddress(address);
        post.setEventDate(dateString);
        post.setBody(body);
        post.setPostType(postTypeDao.findById(2L).get());
        Timestamp timestamp = new Timestamp((new Date()).getTime());
        post.setPostDate(timestamp);
        postDao.save(post);
        return "redirect:/";
    }



    @PostMapping("/post/create/sell")
    public String createSalePost(
            @RequestParam("header") String title,
            @RequestParam("price") Integer price,
            @RequestParam("address") String address,
            @RequestParam("body") String body,
            @RequestParam("id") Long groupId) {
        Post post = new Post();
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(loggedInUser);
        post.setGroup(groupDao.findById(groupId).get());
        post.setHeader(title);
        post.setEventAddress(address);
        post.setPostPrice(price);
        post.setBody(body);
        post.setPostType(postTypeDao.findById(3L).get());
        Timestamp timestamp = new Timestamp((new Date()).getTime());
        post.setPostDate(timestamp);
        postDao.save(post);
        return "redirect:/";
    }



    @PostMapping("/post/create/QandA")
    public String createQandAPost(
            @RequestParam("header") String title,
            @RequestParam("body") String body,
            @RequestParam("id") Long groupId) {
        Post post = new Post();
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(loggedInUser);
        post.setGroup(groupDao.findById(groupId).get());
        post.setHeader(title);
        post.setBody(body);
        post.setPostType(postTypeDao.findById(4L).get());
        Timestamp timestamp = new Timestamp((new Date()).getTime());
        post.setPostDate(timestamp);
        postDao.save(post);
        return "redirect:/";
    }


    @PostMapping("/post/text/edit")
    public String editPost(@RequestParam(name = "header") String header,
                           @RequestParam(name = "body") String body,
                           @RequestParam(name = "id") Long postId)
    {
        try {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User userOfPost = postDao.findById(postId).get().getUser();
            if (userOfPost.getId() == loggedInUser.getId()) {
                Post editedPost = postDao.findById(postId).get();
                editedPost.setHeader(header);
                editedPost.setBody(body);
                postDao.save(editedPost);
            } else {
                System.out.println("User is not the original poster of the post");
            }
        } catch (Exception e) {
            System.out.println("User is not logged in");
            return "redirect:/error";
        }
        return "redirect:/";
    }


    @PostMapping("/post/event/edit")
    public String editPost(@RequestParam(name = "header") String header,
                           @RequestParam(name = "body") String body,
                           @RequestParam(name = "id") Long postId,
                           @RequestParam(name = "eventDate") LocalDate eventDate,
                           @RequestParam(name = "eventAddress") String eventAddress)
    {
        try {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User userOfPost = postDao.findById(postId).get().getUser();
            if (userOfPost.getId() == loggedInUser.getId()) {
                Post editedPost = postDao.findById(postId).get();
                editedPost.setHeader(header);
                editedPost.setBody(body);
                editedPost.setEventDate(eventDate);
                editedPost.setEventAddress(eventAddress);
                postDao.save(editedPost);
            } else {
                System.out.println("User is not the original poster of the post");
            }
        } catch (Exception e) {
            System.out.println("User is not logged in");
            return "redirect:/error";
        }
        return "redirect:/";
    }

    @PostMapping("/post/sale/edit")
    public String editPost(@RequestParam(name = "header") String header,
                           @RequestParam(name = "body") String body,
                           @RequestParam(name = "id") Long postId,
                           @RequestParam(name = "price") int postPrice)
    {
        try {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User userOfPost = postDao.findById(postId).get().getUser();
            if (userOfPost.getId() == loggedInUser.getId()) {
                Post editedPost = postDao.findById(postId).get();
                editedPost.setHeader(header);
                editedPost.setBody(body);
                editedPost.setPostPrice(postPrice);
                postDao.save(editedPost);
            } else {
                System.out.println("User is not the original poster of the post");
            }
        } catch (Exception e) {
            System.out.println("User is not logged in");
            return "redirect:/error";
        }
        return "redirect:/";
    }

    @PostMapping("/post/delete")
    public String deletePost(@RequestParam(name = "id") Long postId, @RequestParam(name = "groupId") Long groupId) {
        try {
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User userOfPost = postDao.findById(postId).get().getUser();
            User groupAdmin = groupDao.findById(groupId).get().getAdmin();
            if (userOfPost.getId() == loggedInUser.getId() || groupAdmin.getId() == loggedInUser.getId()) {
                Post postToBeDeleted = postDao.findById(postId).get();
                postDao.delete(postToBeDeleted);
            } else {
                System.out.println("User is not the original poster of the post or group admin");
            }
        } catch (Exception e) {
            System.out.println("User is not logged in");
            e.printStackTrace();
            return "redirect:/error";
        }
        return "redirect:/";
    }

}
