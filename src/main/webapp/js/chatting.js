const xhr = new XMLHttpRequest();
let fromID = document.querySelector('.userID').innerText;
let toID = document.querySelector('.toID').innerText;
console.log(document.querySelector('.userID').innerText);

// 채팅 내역 db로 보내기
function submitFunction(){
	let chatContent = document.querySelector("#chatInput").value;
	// 비동기 통신
	xhr.open("POST", "./chatSubmetServlet", true);
	//xhr.setRequestHeader("인코딩?방식", ""); 
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState != XMLHttpRequest.DONE) return;
		if(xhr.status == 200){ // 준완
			let result = xhr.response;
			console.log("result : " + result);
			if(result == 1){
				console.log("성공적으로 메세지를 보냈습니다");
			}
			else {
				console.log("메세지 전송에 실패했습니다.");
			}
		}
	}
	xhr.send("fromID=" + fromID + 
		"&toID=" + toID + 
		"&chatContent=" + encodeURIComponent(chatContent)); // header에 포함하고자 하는 key와 값
	document.querySelector("#chatInput").value = ""; // 값 비워주기
}

let lastChatID = 0; // 가장 마지막 채팅의 chatID 값
// 채팅 리스트 불러오기
function chatListFunction(type){
	console.log(type);
	// 비동기 통신
	xhr.open("POST", "./chatListServlet", true);
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
				console.log(result[i][0].value, result[i][2].value, result[i][3].value);
				addChat(result[i][0].value, result[i][2].value, result[i][3].value);
			}
			// chatID값 가져오기
			lastChatID = Number(parsed.last);
		}
	}
	xhr.send("fromID=" + fromID + 
		"&toID=" + toID + 
		"&listType=" + type); // header에 포함하고자 하는 key와 값
}

// 채팅 리스트 화면에 추가
function addChat(chatName, chatContent, chatTime){
	let classType = "";
	// 날짜 , 시간 나눠주기
	let timeArray = chatTime.split(' ');
	timeArray = timeArray.map(element => element.trim());
	let regDate = /^\d{4}-\d{1,2}-\d{1,2}$/;
	let date = ""; // 날짜
	let time = ""; // 시간
	timeArray.forEach(element => {
      if(regDate.test(element)) date += element;
      else {
        time += element + " ";
      }
    });
    
	if(chatName ==fromID) classType = "chatMine";
	var template = `<div class="chatLine ${classType}">
		<span class="chat-box">${chatContent}</span>
		<span class="chat-time">${time}</span>
		</div>`;
	document.querySelector(".chat-content").insertAdjacentHTML("beforeend", template);
}
// 3초 간격으로 새로운 채팅 내용 가져오기
function getInfiniteChat(){
	setInterval(function(){
		chatListFunction(lastChatID);
	}, 1000);
}

// 문서 로딩시
window.addEventListener("load", function(){
	setTimeout(function(){
	chatListFunction('ten');
	getInfiniteChat();
	}, 10)
})

const btn = document.querySelector("#Chatsend");
const chatInput = document.querySelector("#chatInput");

btn.addEventListener("click", submitFunction);
document.addEventListener("keyup", function(e){
	if(e.keyCode === 13 && chatInput.value.length != 0){
		submitFunction();
	}
})