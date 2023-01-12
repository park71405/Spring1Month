console.log("Reply Module......");

var replyService = (function() {
    /*등록처리*/
	function add(reply, callback, error) {
		console.log("add reply.......");

		$.ajax({
			type: 'post',
			url: '/replies/new',
			data: JSON.stringify(reply),
			contentType: "application/json; charset=utf-8",
			success: function(result, status, xhr) {
				if (callback) {
					callback(result);
				}
			},
			error: function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		})
	}
	
	//목록 처리
	function getList(param, callback, error) {
		var bno = param.bno;  //글 번호
		var page = param.page || 1;  //페이지 번호
		
		$.getJSON("/replies/pages/" + bno + "/" + page + ".json",
			function(data) {
				if (callback) {
//					callback(data);  //주석처리 (목록만 가져옴)
					callback(data.replyCnt, data.list);  //추가 (댓글수와 목록을 가져옴)
				}
			}).fail(function(xhr, status, err) {
				if (error) {
					error();
				}
			});
	}
	
	//날짜 형식 출력 함수
	function displayTime(timeValue){
		let today = new Date();
		let gap = today.getTime() - timeValue;
		
		let dateObj = new Date(timeValue);
		let str = "";
		
		if(gap < (1000 * 60 * 60 * 24)){
			let hh = dateObj.getHours();
			let mi = dateObj.getMinutes();
			let ss = dateObj.getSeconds();
			
			return [ (hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi, ':', (ss > 9 ? '' : '0') + ss].join('');
		}else{
			let yy = dateObj.getFullYear();
			let mm = dateObj.getMonth();
			let dd = dateObj.getDate();
			
			return [ yy, '/', (mm > 9 ? '' : '0') + mm, '/', (dd > 9 ? '' : '0') + dd].join('');
		}
		
	};
	
	//조회 처리
	function get(rno, callback, error){
		
		$.get("/replies/" + rno + ".json", function(result){
			if(callback){
				callback(result);
			}
		}).fail(function(xhr, status, err){
			if(error){
				error();
			}
		});
	}
	
	//수정 처리
	function update(reply, callback, error) {

		console.log("RNO: " + reply.rno);

		$.ajax({
			type : 'put',
			url : '/replies/' + reply.rno,
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr) {
				if (callback) {
					callback(result);
				}
			},
			error : function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});
	}
	
	//삭제 처리
	function remove(rno, replyer, callback, error){
		
		$.ajax({
			type: 'delete',
			url: '/replies/' + rno,
			data: JSON.stringify({rno:rno, replyer:replyer}),
			contentType: "application/json; charset=utf-8",
			success: function(deleteResult, status, xhr){
				if(callback){
					callback(deleteResult);
				}
			},
			error: function(xhr, status, er){
				if(error){
					error(er);
				}
			}
		});
	}
	
	return {
		add:add,
		getList:getList,
		displayTime:displayTime,
		get:get,
		update:update,
		remove:remove
	};
})();
//외부에서 add 함수 호출 방법 : replyService.add(객체, 콜백)