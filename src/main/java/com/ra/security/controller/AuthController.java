package com.ra.security.controller;

import com.ra.security.constants.EHttpStatus;
import com.ra.security.model.dto.request.FormLogin;
import com.ra.security.model.dto.request.FormRegister;
import com.ra.security.model.dto.response.ResponseWrapper;
import com.ra.security.model.service.IAuthSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthSevice authSevice;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody FormLogin formLogin) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .ehttpStatus(EHttpStatus.SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .data(authSevice.login(formLogin))
                .build(), HttpStatus.CREATED);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody FormRegister formRegister) {
        authSevice.register(formRegister);
        return new ResponseEntity<>(ResponseWrapper.builder()
                .ehttpStatus(EHttpStatus.SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .data("Register successfully")
                .build(), HttpStatus.CREATED);
    }
}
