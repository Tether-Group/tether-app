package tethergroup.tether;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import tethergroup.tether.services.UserDetailsLoader;

import java.util.Collections;

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
        PortMapperImpl portMapper = new PortMapperImpl();
        portMapper.setPortMappings(Collections.singletonMap("8080","8080"));
        PortResolverImpl portResolver = new PortResolverImpl();
        portResolver.setPortMapper(portMapper);
        LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint(
                "/login");
        entryPoint.setPortMapper(portMapper);
        entryPoint.setPortResolver(portResolver);

        http
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                /* Login configuration */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true) // user's home page, it can be any URL
                .permitAll() // Anyone can go to the login page
                .failureUrl("/login-error")
                /* Logout configuration */
                .and()
                .logout()
                .permitAll()
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
                        "/add-profile-photo",
                        "/verifyAddComments",
                        "/groups/{username}",
                        "/getNotificationCount",
                        "/profile/settings/usernameExists/{attemptedUsername}",
                        "/profile/{username}/friends",
                        "/profile/my-account/groups"
                        )
                .authenticated()
                /* Pages that can be viewed without having to log in */
                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/",
                        "/styles/**",
                        "/js/**",
                        "/img/**",
                        "/register",
                        "/search-results",
                        "/groups",
                        "/group/{groupId}",
                        "/my/logout",
                        "/login-error",
                        "/error",
                        "/about",
                        "/keys.js",
                        "/forgot_password",
                        "/reset_password",
                        "/favicon.ico"
                        )
                .permitAll()
//                .and()
//                .csrf()
//                .ignoringRequestMatchers("/getNotificationCount")

        ;
        return http.build();
    }

}

