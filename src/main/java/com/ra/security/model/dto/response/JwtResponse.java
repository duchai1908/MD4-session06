package com.ra.security.model.dto.response;

import lombok.*;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private String token;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Boolean status;
    private Set<String> roles;
}
