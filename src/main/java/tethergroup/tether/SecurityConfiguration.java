package tethergroup.tether;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tethergroup.tether.services.UserDetailsLoader;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /* Login configuration */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true) // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
                /* Logout configuration */
                .and()
                .logout()
                .logoutSuccessUrl("/") // append a query string value
                /* Pages that require authentication */
                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/post/create",
                        "/post/create/text",
                        "/post/create/event",
                        "/post/create/sell",
                        "/post/create/QandA",
                        "/group/create",
                        "/group/edit",
                        "/group/delete",
                        "/group/{groupId}/join",
                        "/group/{groupId}/leave",
                        "/post/event/{id}",
                        "/posts/{id}/edit",
                        "/post/text/edit",
                        "/post/event/edit",
                        "/post/sale/edit",
                        "/post/delete",
                        "/profile/{username}",
                        "/profile/delete",
                        "/profile/edit",
                        "/profile/settings",
                        "/profile/editpassword",
                        "/notifications",
                        "/notifications/accept/{id}",
                        "/notifications/deny/{id}",
                        "/group/{groupId}/members",
                        "/profile/{userId}/add",
                        "/profile/{userId}/cancel",
                        "/profile/{userId}/remove",
                        "/messages/talk/{username}",
                        "/getUser/{username}",
                        "/getUser/loggedInUser",
                        "/getFriends",
                        "/profile/{userId}/accept",
                        "/profile/{userId}/decline",
                        "/group/{groupId}/{memberId}/remove",
                        "/{postId}/comment/add",
                        "/comment/delete",
                        "/comment/edit",
                        "/verifyAddComments"
                        )
                .authenticated()
                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/",
                        "/styles/**",
                        "/js/**",
                        "/register",
                        "/search-results",
                        "/groups",
                        "/group/{groupId}",
                        "/my/logout",
                        "/error",
                        "/about",
                        "/keys.js"
                        )
                .permitAll()

        ;
        return http.build();
    }

}

