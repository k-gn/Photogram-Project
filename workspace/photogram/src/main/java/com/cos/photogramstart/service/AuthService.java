package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;

import lombok.RequiredArgsConstructor;

@Service // 트랜잭션 관리
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional // Write(insert, update, delete)
	public User signup(User user) {
		
		User present =  userRepository.findByUsername(user.getUsername());
		if (present != null) throw new CustomException("아이디가 중복됩니다.");
		
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 비밀번호 암호화
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		
		User userEntity = userRepository.save(user); // DB 에 들어간 데이터를 반환 (파라미터 User 와 다른 객체)
		
		return userEntity;
	}
}
