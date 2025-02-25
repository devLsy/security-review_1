package com.test.lsy.security2.service;

import com.test.lsy.security2.model.User;
import com.test.lsy.security2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User findUser = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found")
                );

        return org.springframework.security.core.userdetails.User
                .withUsername(findUser.getUsername())
                .password(findUser.getPassword())
                .roles(findUser.getRole())
                .build();
    }
}
