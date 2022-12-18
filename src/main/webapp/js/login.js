const messageWrap = document.getElementById("messageWrap");
const closeBtn = document.querySelector(".closeBtn");

closeBtn.addEventListener("click", ()=>{
	messageWrap.remove();
})