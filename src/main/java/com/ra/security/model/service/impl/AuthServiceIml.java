package com.ra.security.model.service.impl;

import com.ra.security.model.dto.request.FormLogin;
import com.ra.security.model.dto.request.FormRegister;
import com.ra.security.model.dto.response.JwtResponse;
import com.ra.security.model.entity.Role;
import com.ra.security.model.entity.RoleName;
import com.ra.security.model.entity.Users;
import com.ra.security.model.repository.IUsersRepository;
import com.ra.security.model.service.IAuthSevice;
import com.ra.security.model.service.IRoleService;
import com.ra.security.security.jwt.JWTProvider;
import com.ra.security.security.principal.UserDetailCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceIml implements IAuthSevice {
    private final IRoleService roleService;
    private final IUsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    @Override
    public JwtResponse login(FormLogin formLogin) {
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getUsername(), formLogin.getPassword()));
        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password");
        }
        UserDetailCustom userDetailCustom = (UserDetailCustom) authentication.getPrincipal();
        String accessToken = jwtProvider.generateToken(userDetailCustom);

        return JwtResponse.builder()
                .token(accessToken)
                .username(userDetailCustom.getUsername())
                .phone(userDetailCustom.getPhone())
                .email(userDetailCustom.getEmail())
                .address(userDetailCustom.getAddress())
                .fullName(userDetailCustom.getFullName())
                .status(userDetailCustom.getStatus())
                .roles(userDetailCustom.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public void register(FormRegister formRegister) {
        Set<Role> roles = new HashSet<>();
        if(formRegister.getRoles() == null || formRegister.getRoles().isEmpty()) {
                roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        }else{
            formRegister.getRoles().forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                    case "MODERATOR":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_MODERATOR));
                    case "USER":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
                        break;
                    default:
                        throw new RuntimeException("Role not supported");
                }
            });
        }
        Users users = Users.builder()
                .fullName(formRegister.getFullName())
                .password(passwordEncoder.encode(formRegister.getPassword()))
                .username(formRegister.getUsername())
                .roles(roles)
                .build();
        usersRepository.save(users);

    }
}
