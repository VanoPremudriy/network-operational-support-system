package ru.mirea.network.operational.support.system.auth.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import ru.mirea.network.operational.support.system.auth.entity.UserEntity;
import ru.mirea.network.operational.support.system.auth.repository.RoleRepository;
import ru.mirea.network.operational.support.system.auth.repository.UserRepository;

import java.util.function.Consumer;


@Component
@RequiredArgsConstructor
public final class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public void accept(OAuth2User oAuth2User) {
        if (this.userRepository.findUserByUsername(oAuth2User.getName()) == null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(oAuth2User.getName());
            userEntity.setRoleEntityList(roleRepository.findByRoleCodeIn(oAuth2User.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList()));
            userRepository.save(userEntity);
        }
    }
}
