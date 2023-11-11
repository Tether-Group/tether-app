package tethergroup.tether.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(System.getenv("PROD_MAIL_HOST"));
        mailSender.setPort(Integer.parseInt(System.getenv("PROD_MAIL_PORT")));
        mailSender.setUsername(System.getenv("PROD_MAIL_USERNAME"));
        mailSender.setPassword(System.getenv("PROD_MAIL_PASSWORD"));

        return mailSender;
    }
}