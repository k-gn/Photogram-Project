package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

// 어노테이션 없어도 JpaRepository를 상속하면 자동으로 빈 등록이 된다.
public interface UserRepository extends JpaRepository<User, Integer> {

	// JPA Query method
	User findByUsername(String username);
	
	
}
