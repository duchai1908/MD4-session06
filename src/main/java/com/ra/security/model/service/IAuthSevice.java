package com.ra.security.model.service;

import com.ra.security.model.dto.request.FormLogin;
import com.ra.security.model.dto.request.FormRegister;
import com.ra.security.model.dto.response.JwtResponse;

public interface IAuthSevice {
    JwtResponse login(FormLogin formLogin);
    void register(FormRegister formRegister);
}
