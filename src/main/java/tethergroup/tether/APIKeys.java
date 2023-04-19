package tethergroup.tether;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIKeys {
    @Value("${talkJSAppKey}")
    public String talkJSAppKey;

    @Value("${MAPBOX_API_KEY}")
    public String MapboxAPIKey;
}
