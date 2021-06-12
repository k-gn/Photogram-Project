package com.cos.photogramstart.domain.comment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // DB 에 테이블을 생성
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략
	private Integer id; // 대형 프로그램이 아니라 그냥 int 로 해도 충분하다.

	@Column(length = 100, nullable = false)
	private String content; // 댓글 내용
	
	// 기본적으로 딸려오는게 여러개면 lezy, 한개면 eager 전략
	@ManyToOne
	@JoinColumn(name = "userId")
	@JsonIgnoreProperties({"images"})
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "imageId")
	@JsonIgnoreProperties({"likes"})
	private Image image;

	private LocalDateTime createDate;

	@PrePersist // DB 에 INSERT 직전에 자동으로 실행된다. (nativeQuery 같은 걸 쓸 땐 동작 x)
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
