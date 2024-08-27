package com.ra.security.security.principal;

import com.ra.security.model.entity.Users;
import com.ra.security.model.repository.IUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailCustomService implements UserDetailsService {
    private final IUsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = usersRepository.findByUsername(username);
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            return UserDetailCustom.builder()
                    .id(users.getId())
                    .username(users.getUsername())
                    .password(users.getPassword())
                    .address(users.getAddress())
                    .email(users.getEmail())
                    .phone(users.getPhone())
                    .fullName(users.getFullName())
                    .status(users.getStatus())
                    .authorities(users.getRoles().stream()
                            .map(roleNameUser -> new SimpleGrantedAuthority(roleNameUser.getRoleName().name()))
                            .toList())
                    .build();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
