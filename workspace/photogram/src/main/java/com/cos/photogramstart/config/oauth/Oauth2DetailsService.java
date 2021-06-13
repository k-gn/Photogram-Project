package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Oauth2DetailsService extends DefaultOAuth2UserService {
	
	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		System.out.println(oauth2User.getAttributes());
		
		Map<String, Object> userInfo = oauth2User.getAttributes();
		String username = "google_" + (String) userInfo.get("sub");
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
		String email = (String) userInfo.get("email");
		String name = (String) userInfo.get("name");

		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) { // 최초 소셜로그인
			User user = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.name(name)
					.role("ROLE_USER")
					.build();
			
			return new PrincipalDetails(userRepository.save(user), userInfo);
		} else { // 이미 소셜로그인으로 회원가입
			
			return new PrincipalDetails(userEntity, userInfo);
		}
		
	}
}
