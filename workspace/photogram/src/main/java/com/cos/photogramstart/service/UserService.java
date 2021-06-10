package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	// 영속성 컨텍스트는 Service가 끝나는 시점에 변경된 오브젝트를 감지한 후 DB에 자동 flush (더티체킹)
	// readOnly = true -> 변경감지를 안함 -> 예상치 못한 엔티티의 등록, 변경, 삭제를 예방 + 성능향상 (조회 기능만 가능)
	@Transactional(readOnly = true)
	public User userProfile(int userId) {
		// SELECT * FROM image WHERE userId = :userId; 
		User userEntity = userRepository.findById(userId).orElseThrow(() -> new CustomException("해당 프로필 페이지는 없는 페이지 입니다."));
		return userEntity;
	}
	
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
