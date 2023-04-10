package tethergroup.tether.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String returnLandingPage() {return "index";}

    @GetMapping("/search-results")
    public String returnSearchResultsPage() {return "search-results";}
}
