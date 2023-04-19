package tethergroup.tether.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tethergroup.tether.APIKeys;

@AllArgsConstructor
@Controller
public class KeysController {
    private APIKeys apiKeys;

    @GetMapping(value = "/keys.js", produces = "text/javascript")
    @ResponseBody
    public String getAPIKeys() {
        return "const MAPBOX_API_KEY = \"" + apiKeys.MapboxAPIKey + "\";\n" +
                "const app_id = \"" + apiKeys.talkJSAppKey + "\";";
    }
}
