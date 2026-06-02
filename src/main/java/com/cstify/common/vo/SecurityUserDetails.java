package com.cstify.common.vo;

import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record SecurityUserDetails(UserInfo userInfo) implements UserDetails {

    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities;
        if (CollectionUtils.isEmpty(userInfo.getRoles())) {
            authorities = getGrantedAuthorities(userInfo.getRoleCode());
        } else {
            authorities = getGrantedAuthorities(userInfo.getRoles());
        }
        return authorities;
    }

    @Override
    @NullMarked
    public String getUsername() {
        return userInfo.getLoginId();
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    private List<GrantedAuthority> getGrantedAuthorities(String userGrade) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + userGrade));
        return authorities;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> userAuthorities) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userAuthorities)) {
            userAuthorities.forEach(auth -> authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + auth)));
        }
        return authorities;
    }
}
