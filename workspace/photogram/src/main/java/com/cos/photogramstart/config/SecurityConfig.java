package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.config.oauth.Oauth2DetailsService;

import lombok.RequiredArgsConstructor;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final Oauth2DetailsService oauth2DetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http); // 애 때문에 기본적으로 시큐리티가 제공하는 로그인화면이 뜬다, 삭제 시 기존 시큐리티가 가지고 있는 기능이 비활성화된다.
		
		http.authorizeRequests()
			.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
				.loginPage("/auth/signin")
				.loginProcessingUrl("/auth/signin") // POST -> 스프링 시큐리티가 로그인 프로세스 진행.
				.defaultSuccessUrl("/") // true를 추가하면 로그인 성공 시 무조건 / 로 이동하고 안하면 이전 접근 페이지로 이동한다. (단. LoginSuccessHandler가 따로 없을 경우)
				.and()
				.oauth2Login() // oauth2 로그인도 할꺼야~
				.userInfoEndpoint() // oauth2 로그인을 하면 최종 응답을 회원정보로 바로 받을 수 있음. (자동으로 code 처리 및 accessToken 처리)
				.userService(oauth2DetailsService); // oauth2용 userService 설정
		
		http.csrf().disable(); 
	}
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
}
