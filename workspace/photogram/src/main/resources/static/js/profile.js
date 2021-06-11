/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
// $( 'p' ).toggleClass( 'xyz' );. 는, p 요소에 xyz 클래스가 없으면 추가하고, 있으면 제거
function toggleSubscribe(toUserId, obj) {
	if ($(obj).text() === "구독취소") {
		
		$.ajax({
			type: "delete",
			url: "/api/subscribe/" + toUserId,
			dataType: "json"
		}).done(res => {
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
		}).fail(error => {
			console.log("구독취소 실패", error);
		})
	} else {
		
		$.ajax({
			type: "post",
			url: "/api/subscribe/" + toUserId,
			dataType: "json"
		}).done(res => {
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");
		}).fail(error => {
			console.log("구독하기 실패", error);
		})
	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
	$(".modal-subscribe").css("display", "flex");
	
	$.ajax({
			url: `/api/user/${pageUserId}/subscribe`,
			dataType: "json"
		}).done(res => {
			console.log(res.data);
			
			res.data.forEach(u => {
				let item = getSubscribeModalItem(u);
				$("#subscribeModalList").append(item);
			});
		}).fail(error => {
			console.log(error);
		})
}

function getSubscribeModalItem(u) {
	let item = `<div class="subscribe__item" id="subscribeModalItem-${u.id}">
						<div class="subscribe__img">
							<img src="${u.profileImageUrl}" onerror="this.src='/images/person.jpeg'" />
						</div>
						<div class="subscribe__text">
							<h2>${u.username}</h2>
						</div>
						<div class="subscribe__btn">`;
						
	if(!u.equalUserState) {
		if(u.subscribeState) {
			item += `<button class="cta blue" onclick="toggleSubscribe(${u.id}, this)">구독취소</button>`;
		}else {
			item += `<button class="cta" onclick="toggleSubscribe(${u.id}, this)">구독하기</button>`;
		}
	}
						
	item += `
						</div>
					</div>`;
							
	return item;
}


// (3) 유저 프로파일 사진 변경 (완)
function profileImageUpload(pageUserId, principalId) {
	
	if(pageUserId !== principalId) {
		alert("프로필 사진을 수정할 수 없는 유저입니다.");
		return;	
	}
	
	$("#userProfileImageInput").click();
	

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}

		// 서버에 프로필 이미지 전송
		let profileImageForm = $("#userProfileImageForm")[0];
		
		// FormData 객체를 이용하면 form 태그의 필드와 그 값을 나타내는 일련의 key/value 쌍을 담을 수 있다. + 파일, 이미지 등 다양하게 전송할 목적
		let formData = new FormData(profileImageForm);
		
		// Ajax로 multipart/form-data 요청 보내기 
		$.ajax({
			type: "put",
			url: `/api/user/${principalId}/profileImageUrl`,
			data: formData,
			contentType: false, // 필수 : x-www-form-urlencoded로 파싱되는것을 방지
			processData: false, // 필수 : contentType: false 일 때 QueryString 이 자동으로 설정되는 것을 해제
			enctype: "multipart/form-data",
			dataType: "json"
		}).done(res => {
			// 사진 전송 성공시 이미지 변경
			let reader = new FileReader();
			reader.onload = (e) => {
				$("#userProfileImage").attr("src", e.target.result);
			}
			reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
		}).fail(error => {
			console.log(error);
		});
		
	});
}


// (4) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}






