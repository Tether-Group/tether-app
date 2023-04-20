package tethergroup.tether.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tethergroup.tether.models.User;
import tethergroup.tether.repositories.UserRepository;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service
@Transactional
public class UserServices {

    @Autowired
    private UserRepository userDao;

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userDao.findUserByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userDao.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userDao.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userDao.save(user);
    }

}
