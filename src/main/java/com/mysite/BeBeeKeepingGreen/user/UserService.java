package com.mysite.BeBeeKeepingGreen.user;

import com.mysite.BeBeeKeepingGreen.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String location, String password, String x, String y){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setLocation(location);
        user.setPassword(passwordEncoder.encode(password));
        user.setX(x);
        user.setY(y);
        this.userRepository.save(user);

        return user;
    }

    public SiteUser getUser(String username){
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if(siteUser.isPresent()){
            return siteUser.get();
        }else{
            throw new DataNotFoundException("사용자 찾지 못함");
        }
    }

    public List<SiteUser> getAllUser(){
        List<SiteUser> userList = userRepository.findAllBy();
        return userList;
    }

    public List<String> getAllUserLocations(){
        List<SiteUser> users = userRepository.findAllBy();
        List<String> locations = new ArrayList<>();
        for(SiteUser user : users){
            locations.add(user.getLocation());
        }

        return locations;
    }

}
