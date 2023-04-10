package tethergroup.tether.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@RequiredArgsConstructor
@Controller
public class HomeController {

    @GetMapping("/")
    public String returnLandingPage() {return "index";}

    @GetMapping("/search-results")
    public String returnSearchResultsPage() {return "search-results";}
}
