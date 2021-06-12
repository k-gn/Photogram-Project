package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// NotNull = Null값 체크
// NotEmpty = 빈값이거나 NULL 체크
// NotBlank = 빈값이거나 NULL이거나 빈 공백(스페이스) 체크
import lombok.Data;

@Data
public class CommentDto {

	@NotBlank
	private String content;
	
	@NotNull
	private Integer imageId;
	
}
