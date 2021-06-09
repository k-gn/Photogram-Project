// (1) 회원정보 수정
function update(userId, event) {
	event.preventDefault() ; // 폼 태그의 액션을 막기
	console.log(event); // 이벤트가 자동으로 전송된다.
	
	let data = $("#profileUpdate").serialize(); // Form 의 모든 input 값이 담긴다.
	
	console.log(data);
	
	// 데이터를 응답해주는 것이 api 서버
	// Ajax 요청 보내기
	$.ajax({
		type: "put", // 보낼 타입
		url: `/api/user/${userId}`, // 보낼 URI
		data: data, // 보낼 데이터
		contentType: "application/x-www-form-urlencoded; charset=utf-8", // MimeType, encoding 등 기타 헤더 설정
		dataType: "json" // 받을 데이터 타입
	}).done(res => { // 요청 성공 시 (HttpStatus 200 번대)
		console.log("성공", res);
		location.href=`/user/${userId}`;
	}).fail(error => { // 요청 실패 시 (HttpStatus 200 번대가 아닐 때)
		if(error.data == null) {
			alert(error.responseJSON.message);			
		}else {
			alert(JSON.stringify(error.responseJSON.data)); // JSON.stringify : Javascript Object -> JSON 문자열로 변환
		}
		console.log("실패", error);
	});
}