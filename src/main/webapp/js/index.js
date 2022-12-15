const aTag = document.querySelector(".chatBtn");

aTag.addEventListener("click", (e)=>{
  e.preventDefault();
  window.open("./chatMain.jsp", "채팅", " width=400, height=500");
})