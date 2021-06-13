package com.cos.photogramstart.domain.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(
		uniqueConstraints = { // unique 제약조건 설정
				@UniqueConstraint(
						name = "subscribe_uk",
						columnNames = {"fromUserId", "toUserId"}
				)
		}
)
public class Subscribe {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; 

	@ManyToOne 
	@JoinColumn(name = "fromUserId") // 해당하는 객체를 참조하는 외래키를 해당 name명으로 만들어 준다.
	@ToString.Exclude
	private User fromUser; // 구독하는 사람
	
	@ManyToOne
	@JoinColumn(name = "toUserId")
	@ToString.Exclude
	private User toUser; // 구독받는 사람

	private LocalDateTime createDate;
	
	@PrePersist // DB 에 INSERT 직전에 자동으로 실행된다.
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
