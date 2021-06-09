package com.cos.photogramstart.web.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {

	@NotBlank
	private String name; // 필수
	
	@NotBlank
	private String password; // 필수
	
	private String website;
	
	private String phone;
	
	private String bio;
	
	private String gender;
	
	// 조금 위험, 코드 수정 필요할 예정 -> validation 체크 필요!
	public User toEntity() {
		return User.builder()
				.name(name)
				.password(password)
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
