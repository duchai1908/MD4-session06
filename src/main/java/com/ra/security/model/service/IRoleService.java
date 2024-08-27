package com.ra.security.model.service;

import com.ra.security.model.entity.Role;
import com.ra.security.model.entity.RoleName;

public interface IRoleService {
    Role findByRoleName(RoleName roleName);
}
