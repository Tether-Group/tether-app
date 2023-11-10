package tethergroup.tether;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIKeys {

    @Value("${TALK_JS_APP_KEY}")
    public String talkJSAppKey;

    @Value("${MAPBOX_API_KEY}")
    public String mapBoxAPIKey;

    @Value("${FILESTACK_API_KEY}")
    public String filestackAPIKey;
}
