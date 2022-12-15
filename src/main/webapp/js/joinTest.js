const form = document.joinForm;
const submitBtn = form.submitBtn
let checkID_State = false;
const idInputTag = form.userID;
console.log(idInputTag);

// 아이디 중복 검사 재체크 필요
idInputTag.addEventListener("input", ()=>{
	checkID_State = false;
})

function insert(){ // 유효성 검사 밑 submit
  let id = form.userID.value;
  let pw = form.userPassword.value;
  let pwCheck = form.userPWCheck.value;
  let name = form.userName.value;
  // console.log(id, pw, pwCheck, name, email);
  
  // 아이디 정규표현식
  let regExpIdFirst = /^[a-z|A-Z]/; // 첫글자 영문
  let regEpxIdNext = /^[a-z|A-Z|0-9]{4,12}$/; // 이후 글자 영or숫자 사용
  // 비밀번호 정규표현식
  let regExpPasswd = /^[0-9]{6,12}$/;
  // 이름 정규표현식
  let regExpName = /^[가-힣]{2,4}$/;
  
  // 아이디 중복검사 체크
  if(!checkID_State){
	alert("아이디 중복검사를 진행한 후 가입 버튼을 눌러주세요.");
	return;
  }
  // 아이디 유효성 검사
  if(id.length == 0){ // 미입력시
    alert("아이디를 입력해주세요.");
    form.userID.select();
    return;
  }
  else if(!regExpIdFirst.test(id)){
    alert("아이디는 영문자로 시작해야합니다.");
    form.userID.select();
    return;
  }
  else if(!regEpxIdNext.test(id)){
    // console.log("else if");
    alert("아이디는 영문자 또는 숫자를 사용하여 4자이상 12자 이하로 작성하세요.");
    form.userID.select();
    return;
  }
  // 비밀번호 유효성 검사
  if(pw.length == 0){ // 미입력시
    alert("비밀번호를 입력해주세요.");
    form.userPassword.select();
    return;
  }
  else if(!regExpPasswd.test(pw)){
    alert("비밀번호는 숫자만을 이용해서 6자이상 12자 이하로 작성해주세요.");
    form.userPassword.select();
    return;
  }
  // 비밀번호 확인
  if(pwCheck != pw){
    alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
    form.userPWCheck.select();
    return;
  }
  // 이름 유효성 검사
  if(name.length == 0){ // 미입력시
    alert("이름을 입력해주세요.");
    form.userName.select();
    return;
  }
  else if(!regExpName.test(name)){
    alert("이름은 한글만을 이용해서 2자이상 4자이하로 입력해 주세요!");
    form.userName.select();
    return;
  }
  
  alert("회원가입에 성공했습니다!");
  form.submit();
}

// 가입하기 버튼 클릭 -> 가입 실행
submitBtn.addEventListener("click", insert);

// 아이디 중복검사
const xhr = new XMLHttpRequest();
const checkIDBtn = document.getElementById("checkIDBtn");


const checkID = function(){
	let userIDVal = form.userID.value; // userID 값 가져오기
		console.log(userIDVal);
	xhr.open("POST", "./UserRegisterCheckServlet", true);
	//xhr.setRequestHeader("인코딩?방식", ""); 
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState != XMLHttpRequest.DONE) return;
		console.log("test");
		if(xhr.status == 200){ // 준완
			let result = xhr.response;
			console.log("result : " + result);
			if(result == 1){ // 가입 가능한 회원
				alert("사용가능한 아이디입니다.");
				checkID_State = true;
			}
			else {
				alert("사용할 수 없는 아이디입니다.");
			}
		}
	}
	xhr.send("userID=" + userIDVal); // header에 포함하고자 하는 key와 값
} 
checkIDBtn.addEventListener("click", checkID);

