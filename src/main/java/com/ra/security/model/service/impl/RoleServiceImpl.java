package com.ra.security.model.service.impl;

import com.ra.security.model.entity.Role;
import com.ra.security.model.entity.RoleName;
import com.ra.security.model.repository.IRoleRepository;
import com.ra.security.model.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;
    @Override
    public Role findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(()->new RuntimeException("Role not found"));
    }
}
