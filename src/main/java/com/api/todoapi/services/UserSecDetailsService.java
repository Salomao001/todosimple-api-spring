package com.api.todoapi.services;

import com.api.todoapi.models.User;
import com.api.todoapi.security.UserSecurityDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserSecurityDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        return new UserSecurityDetails(user.getId(), user.getUsername(), user.getPassword(), user.getRoles());
    }
}
