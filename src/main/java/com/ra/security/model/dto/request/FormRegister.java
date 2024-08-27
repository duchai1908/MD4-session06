package com.ra.security.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FormRegister {
    private String username;
    private String password;
    private String fullName;
    private Set<String> roles;
}
