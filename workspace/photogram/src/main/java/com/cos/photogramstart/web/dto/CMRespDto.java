package com.cos.photogramstart.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 공통 응답 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CMRespDto<T> {

	private int code; // 응답 코드 (1 : 성공, -1 : 실패)
	private String message;
	private T data;
	
}
