/**
 * 
 */

// 타자 검증에 사용하는 정규표현식
let reUid 	= /^[a-z]+[a-z0-9]{5,19}$/g;
let rePass 	= /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
let reName 	= /^[ㄱ-힣]+$/;
let reNick 	= /^[a-zA-Zㄱ-힣0-9][a-zA-Zㄱ-힣0-9]*$/;
let reEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
let reHp 	= /^01(?:0|1|[6-9])-(?:\d{4})-\d{4}$/;

let isUidOk 	= false;
let isPassOk 	= false;
let isNameOk 	= false;
let isNickOk 	= false;
let isEmailOk	= false;
let isHpOk 		= false;

// window > javascript 내장객체
window.onload = function(){

	const xhr = new XMLHttpRequest();
	const btnCheckUid = document.getElementById('btnCheckUid');
	const pass2 = document.getElementsByName('pass2');
	const btnCheckNick = document.getElementById('btnCheckNick');
	
	// 버튼을 눌렀을때 이벤트
	btnCheckUid.addEventListener('click', ()=>{

		// 선택자 함수
		let uid = document.querySelector('input[name=uid]').value;
		const resultUid = document.querySelector('.resultUid');
		
		if(!uid.match(reUid)){
			resultUid.innerText = '유효한 아이디가 아닙니다.';
			resultUid.style.color = 'red';
			return;
		}
		
		// AJAX 전송
		xhr.open("GET", "/Farmstory/user/checkUid?uid="+uid);
		xhr.responseType = "json";
		xhr.send();
		
		xhr.onreadystatechange = function(){
			
			if(xhr.readyState == XMLHttpRequest.DONE){
				
				// 데이터 처리 성공했을 경우
				if(xhr.status == 200){
					const data = xhr.response;
					console.log(data);
					
					
					if(data.result == 1){
						resultUid.innerText = '이미 사용중인 아이디 입니다.';
						resultUid.style.color = 'red';
					}else{
						resultUid.innerText = '사용 가능한 아이디 입니다.';
						resultUid.style.color = 'green';
						isUidOk = true;
					}
					
				}else{
					alert("Request fail...");
				}
			}
		}
	});
	
	// 비밀번호 유효성 검사
	pass2[0].addEventListener('focusout', ()=>{
		
		let pass1 = document.querySelector('input[name=pass1]').value;
		let pass2 = document.querySelector('input[name=pass2]').value;
		let resultPass = document.querySelector('.resultPass');
		
		if(pass2.match(rePass)){
			
			if(pass1 == pass2){
				resultPass.innerText = '사용 가능한 비밀번호 입니다.';
				resultPass.style.color = 'green';
				isPassOk = true;
			}else{
				resultPass.innerText = '비밀번호가 일치하지 않습니다.';
				resultPass.style.color = 'red';
			}
			
		}else{
			resultPass.innerText = '비밀번호는 숫자, 영문, 특수문자 포함 5자리 이상 이어야 합니다.';
			resultPass.style.color = 'red';
		}
		
	});
	
	// 이름 유효성 검사
	document.querySelector('input[name=name]').addEventListener('focusout', ()=>{
		let name = document.querySelector('input[name=name]').value;
		let resultName = document.querySelector('.resultName');
		
		if(!name.match(reName)){
			resultName.innerText = '유효하지 않은 이름입니다.';
			resultName.style.color = 'red';
			return;
		}else{
			resultName.innerText = '';
			isNameOk = true;
		}
		
	});
	
	// 별명 검사
	btnCheckNick.addEventListener('click', ()=>{
		
		let nick = document.querySelector('input[name=nick]').value;
		let resultNick = document.querySelector('.resultNick');
		
		if(!nick.match(reNick)){
			resultNick.innerText = '유효한 별명이 아닙니다.';
			resultNick.style.color = 'red';
			return;
		}
		
		xhr.open("GET", "/Farmstory/user/checkNick?nick="+nick);
		xhr.responseType = "json";
		xhr.send();
		
		xhr.onreadystatechange = function(){
			
			if(xhr.readyState == XMLHttpRequest.DONE){
				if(xhr.status == 200){
					const data = xhr.response;
					
					if(data.result == 1){
						resultNick.innerText = '이미 사용중인 별명입니다.';
	    				resultNick.style.color = 'red';
					}else{
						resultNick.innerText = '사용 가능한 별명입니다.';
	    				resultNick.style.color = 'green';
	    				isNickOk = true;
					}
				}
			}
		}
		
	});
	
	document.querySelector('input[name=email').addEventListener('focusout', ()=>{
		
		let email = document.querySelector('input[name=email]').value;
		let resultEmail = document.querySelector('.resultEmail');
		
		if(!email.match(reEmail)){
			resultEmail.innerText = '유효하지 않은 이메일 입니다.';
			resultEmail.style.color = 'red';
		}else{
			resultEmail.innerText = '';
			isEmailOk = true;
		}
		
		
	});
	
	document.querySelector('input[name=hp]').addEventListener('focusout', ()=>{
		let hp = document.querySelector('input[name=hp]').value;
		let resultHp = document.querySelector('.resultHp');
		
		if(!hp.match(reHp)){
			resultHp.innerText = '유효하지 않은 번호 입니다.';
			resultHp.style.color = 'red';
		}else{
			resultHp.innerText = '';
			isHpOk = true;
		}
		
		
	});

	// 최종검사
	document.querySelector('form').onsubmit = ()=>{
		if(!isUidOk){
			alert('아이디를 확인 해 주십시오.');
			return false;					
		}
		
		if(!isPassOk){
			alert('비밀번호를 확인 해 주십시오.');
			return false;
		}
		
		if(!isNameOk){
			alert('이름을 확인 해 주십시오.');
			return false;
		}
		
		if(!isNickOk){
			alert('별명을 확인 해 주십시오.');
			return false;
		}
		
		if(!isEmailOk){
			alert('이메일을 확인 해 주십시오.');
			return false;
		}
		
		if(!isHpOk){
			alert('전화번호를 확인 해 주십시오.');
			return false;
		}
	};
	
}