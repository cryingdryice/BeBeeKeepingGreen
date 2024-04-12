package user;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String location, String password){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setLocation(location);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);

        return user;
    }
}
