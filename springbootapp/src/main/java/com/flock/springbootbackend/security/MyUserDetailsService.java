package com.flock.springbootbackend.security;

import com.flock.springbootbackend.Utils;
import com.flock.springbootbackend.repository.UserRepo;
import com.flock.springbootbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> userRes = userRepo.findByEmail(email);

        if(userRes.isEmpty())
            throw new UsernameNotFoundException(Utils.AuthContants.USER_NOT_FOUND_WITH_EMAIL + email);

        User user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                email,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(Utils.AuthContants.ROLE_USER)));
    }
}
