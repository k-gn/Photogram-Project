package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String caption;

	private String postImageUrl; // 파일 경로

	@ManyToOne(fetch = FetchType.EAGER) // 이미지를 select 하면 user 정보를 같이 가져온다.
	@JoinColumn(name = "userId")
	@ToString.Exclude
	private User user; // 누가 업로드 했는지
	
	// 이미지 좋아요
	
	// 댓글

	private LocalDateTime createDate;

	@PrePersist // DB 에 INSERT 직전에 자동으로 실행된다.
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
