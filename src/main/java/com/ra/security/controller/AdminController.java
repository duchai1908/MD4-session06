package com.ra.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @GetMapping("api/v1/admin")
    public ResponseEntity<?> admin() {
        return ResponseEntity.ok("admin");
    }
}
