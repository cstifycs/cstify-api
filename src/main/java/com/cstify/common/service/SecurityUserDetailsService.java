package com.cstify.common.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityUserDetailsService extends UserDetailsService {
    UserDetails loadUserByUserNo(Long userNo);
}
