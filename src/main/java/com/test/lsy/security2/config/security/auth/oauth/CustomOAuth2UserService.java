package com.test.lsy.security2.config.security.auth.oauth;

import com.test.lsy.security2.config.security.auth.PrincipalDetails;
import com.test.lsy.security2.model.User;
import com.test.lsy.security2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes: {}", oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = (String)oAuth2User.getAttribute("sub");
        String username = provider + "_" + providerId;
        String role = "ROLE_USER";
        String email = (String)oAuth2User.getAttribute("email");

        Optional<User> findUser = repository.findByUsername(username);

        User user;

        if(findUser.isEmpty()) {
            user = User.builder()
                    .username(username)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .prodiverId(providerId)
                    .build();

            repository.save(user);
        } else {
            user = findUser.get();
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
