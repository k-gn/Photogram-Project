package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	// 영속성 컨텍스트는 Service가 끝나는 시점에 변경된 오브젝트를 감지한 후 DB에 자동 flush (더티체킹)
	// readOnly = true -> 변경감지를 안함 -> 예상치 못한 엔티티의 등록, 변경, 삭제를 예방 + 성능향상 (조회 기능만 가능)
	@Transactional(readOnly = true)
	public UserProfileDto userProfile(int pageUserId, int principalId) {
		// SELECT * FROM image WHERE userId = :userId; 
		User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> new CustomException("해당 프로필 페이지는 없는 페이지 입니다."));
		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
		
		UserProfileDto dto = UserProfileDto.builder()
				.user(userEntity)
				.pageOwner(pageUserId == principalId) // 1은 페이지 주인, -1은 주인이 아님
				.imageCount(userEntity.getImages().size())
				.subscribeCount(subscribeCount)
				.subscribeState(subscribeState == 1)
				.build();
		
		return dto;
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
