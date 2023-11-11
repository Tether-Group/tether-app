package tethergroup.tether;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-prod.yaml")
public class APIKeys {

    @Value("${talkJSAppKey}")
    public String talkJSAppKey;

    @Value("${MAPBOX_API_KEY}")
    public String MapboxAPIKey;

    @Value("${FILESTACK_API_KEY}")
    public String filestackAPIKey;

}
