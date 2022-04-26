package com.uni.vrk.teachingcenter.security;

import com.uni.vrk.teachingcenter.dao.UserC;
import com.uni.vrk.teachingcenter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserC userC = userRepository.findUserCByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User:" + username + " not found"));
        return new UserDetailsImpl(userC);
    }
}
