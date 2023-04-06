package tethergroup.tether.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GroupController {

    @GetMapping("/group")
    public String returnGroupPage() {return "groups/group";}

    @GetMapping ("/groups")
    String returnGroupsListPage() {return "groups/group-list";}

    @GetMapping("/members")
    public String returnMembersListPage() {return "groups/members";}
}
