/**
 * 
 */

	$(document).ready(function(){
				
		// member 목록1
		$('.member.list1').click(function(){
			
			$.ajax({
				url: '/Ch09/member',
				method: 'GET',
				dataType: 'json',
				success: function(data){
					console.log(data);
				}
			});
		});
		
		// member 목록2
		$('.member.list2').click(function(){

			let uid = 'A104';
			
			$.ajax({
				url: '/Ch09/member/'+uid,
				method: 'GET',
				dataType: 'json',
				success: function(data){
					console.log(data);
				}
			});
		});
		
		// member 등록
		$('.member.register').click(function(){
			
			let jsonData = {
				"uid":"S101",	
				"name":"홍길동",	
				"hp":"010-1234-1101",	
				"age":19,	
			};
			
			$.ajax({
				url: '/Ch09/member/',
				method: 'POST',
				data : jsonData,
				dataType: 'json',
				success: function(data){
					console.log(data);
				}
			});
		});
		
		// member 수정
		$('.member.modify').click(function(){
			
			let jsonData = {
					"uid":"S101",	
					"name":"홍길동",	
					"hp":"010-1234-2202",	
					"age":19,	
				};
				
				$.ajax({
					url: '/Ch09/member/',
					method: 'PUT',
					data : jsonData,
					dataType: 'json',
					success: function(data){
						console.log(data);
					}
				});
		});
		
		// member 삭제
		$('.member.delete').click(function(){
			let uid = 'S101';
			
			$.ajax({
				url: '/Ch09/member/'+uid,
				method: 'DELETE',
				dataType: 'json',
				success: function(data){
					console.log(data);
				}
			});
		});
	});