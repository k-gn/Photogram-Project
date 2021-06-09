package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Transactional
	public User update(int id, User user) {
		// 1. 영속화
		// Optional : 1. 무조건 찾았다. - get(), 2. 못 찾아서 에러 ㅠ - orElseThrow()
		User userEntity = userRepository.findById(id).orElseThrow(() -> new CustomValidationApiException("찾을 수 없는 id 입니다.")); 
		
		// 2. 영속화된 오브젝트 수정 
		String rawPassword = user.getPassword();
		String encPassword = passwordEncoder.encode(rawPassword);
		userEntity.setName(user.getName());
		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		
		// 더티체킹 (업데이트 완료)
		return userEntity;
	}
}
