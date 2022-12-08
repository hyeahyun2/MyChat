/**
 * 
 */
const btn = document.querySelector("#Chatsend");
const chatInput = document.querySelector("#chatInput");
function sendChat(){
	let chatInput_v = document.querySelector("#chatInput").value;
	var template = `<div class="chatLine">
		<span class="chat-box chatMine">${chatInput_v}</span>
		</div>`;
	document.querySelector(".chat-content").insertAdjacentHTML("beforeend", template);
	document.querySelector("#chatInput").value = null;
}

btn.addEventListener("click", sendChat);
document.addEventListener("keyup", function(e){
	if(e.keyCode === 13 && chatInput.value.length != 0){
		sendChat();
	}
})