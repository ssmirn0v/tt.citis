package com.uni.vrk.teachingcenter.security;

import com.uni.vrk.teachingcenter.dao.UserC;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class UserDetailsImpl implements UserDetails {
    private UserC user;

    public String getId() {
        return user.getUserId();
    }

    public UserDetailsImpl(UserC userc) {
        this.user = userc;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getRoles().stream()
                .map(role -> role.getName().toString())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

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
}
