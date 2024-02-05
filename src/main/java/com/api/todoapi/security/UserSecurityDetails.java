package com.api.todoapi.security;

import com.api.todoapi.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class UserSecurityDetails implements UserDetails {

    public UserSecurityDetails(long id, String username, String password, Set<UserRole> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = roles.stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toList());
    }

    private long id;

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(UserRole userRole) {
        return getAuthorities().contains(new SimpleGrantedAuthority(userRole.getDescription()));
    }
}
