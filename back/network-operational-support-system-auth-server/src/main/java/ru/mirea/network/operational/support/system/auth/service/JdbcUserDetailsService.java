package ru.mirea.network.operational.support.system.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import ru.mirea.network.operational.support.system.auth.entity.RoleEntity;
import ru.mirea.network.operational.support.system.auth.entity.UserEntity;
import ru.mirea.network.operational.support.system.auth.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JdbcUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByUsername(username);
        if (ObjectUtils.isEmpty(userEntity)) {
            throw new UsernameNotFoundException("user is not found");
        }
        if (CollectionUtils.isEmpty(userEntity.getRoleEntityList())) {
            throw new UsernameNotFoundException("role is not found");
        }
        Set<SimpleGrantedAuthority> authorities = userEntity.getRoleEntityList().stream()
                .map(RoleEntity::getRoleCode)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }
}
