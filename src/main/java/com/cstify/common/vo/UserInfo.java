package com.cstify.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Schema
public class UserInfo  implements Serializable {

    @Serial
    private static final long serialVersionUID = -6790877192432019314L;

    private String channel;

    private String clientIP;

    private String domain;

    private Long userId;

    private String password;

    private List<GrantedAuthority> authorities;

    private List<String> roles;

    private Long partnerId;

    private Long parentPartnerId;

    private Long tenantId;

    private String partnerCode;

    private String loginId;

    private String userName;

    private String email;

    private String phone;

    private String telegramId;

    private String roleCode;
}
