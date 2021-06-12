package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
// 여러 데이터가 모여져서 만들어지면 오브젝트(테이블) 로 만들어야 한다.

// JPA - Java Persistence API (자바로 데이터를 영구적으로 저장할 수 있는 api)
@Entity // DB 에 테이블을 생성
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략
	private Integer id; // 대형 프로그램이 아니라 그냥 int 로 해도 충분하다.
	
	@Column(length = 20, nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	private String website;
	private String bio; // 자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl; // 사진
	private String role; // 권한
	
	// # 양방향 매핑
	// mappedBy : 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지마! + 관계의 주인이 아닌쪽은 readOnly (images 조회만 가능) 
	// User를 SELECT 할 때 해당 User id 로 등록된 image를 다 가져와!
	// LAZY : User를 SELECT 할 때 해당 User id 로 등록된 image를 다 가져오지마! -> 대신 image가 호출될 때 가져와!
	// EAGER = User를 SELECT 할 때 해당 User id 로 등록된 image를 조인해서 전부 가져와!
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"user"}) // 해당 컬럼을 JSON 으로 파싱하지 않는다. (get 무한참조 방지)
	private List<Image> images;
	
	private LocalDateTime createDate;
	
	@PrePersist // DB 에 INSERT 직전에 자동으로 실행된다. (nativeQuery 같은 걸 쓸 땐 동작 x)
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
