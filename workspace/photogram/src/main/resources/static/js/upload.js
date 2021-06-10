// (1) 스토리 이미지 업로드를 위한 사진 선택 로직
function imageChoose(obj) {
	let f = obj.files[0]; // 파일 정보 가져오기

	console.log(f);
	
	if (!f.type.match("image.*")) { // 파일 타입 검사
		alert("이미지를 등록해야 합니다.");
		return;
	}

	let reader = new FileReader();
	reader.onload = (e) => { // 읽기 동작이 성공적으로 완료되었을 때마다 발생
		console.log(e);
		console.log(e.target);
		console.log(e.target.result); // 이미지에 대한 경로
		$("#imageUploadPreview").attr("src", e.target.result); // img 태그의 src 속성 변경
	}
	reader.readAsDataURL(f); // 파일의 URL을 읽어 드린다, 이 코드 실행시 reader.onload 실행됨.
}