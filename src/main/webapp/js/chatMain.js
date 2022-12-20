const xhr = new XMLHttpRequest();
// 채팅 리스트 불러오기
let userID = document.querySelector('.userID').innerText;
function chatIDListFunction(){
	// 비동기 통신
	xhr.open("POST", "./chatIDListServlet", true);
	//xhr.setRequestHeader("인코딩?방식", ""); 
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState != XMLHttpRequest.DONE) return;
		if(xhr.status == 200){ // 준완
			let data = xhr.response;
			if(data == "") return; // 공백일 경우 제외
			let parsed = JSON.parse(data); // json형태로 파싱
			let result = parsed.result;	
			for(let i=0; i<result.length; i++){
				console.log(result[i].chatUserID);
				if(result[i].chatUserID == userID) continue; // userID인 경우 제외
				addIDList(result[i].chatUserID);
			}
			// chatID값 가져오기
			lastChatID = Number(parsed.last);
		}
	}
	xhr.send("userID=" + userID); // header에 포함하고자 하는 key와 값
}

function addIDList(chatUserID){
	var template = `<li class="chatUser">
						<a href="./chatting.jsp?toID=${chatUserID}">
					        <div></div>
					        <span>${chatUserID}</span>
				    	</a>
				    </li>`;
	document.querySelector("#chatUserList").insertAdjacentHTML("beforeend", template);
}

// 문서 로딩시
window.addEventListener("load", function(){
	chatIDListFunction();
})