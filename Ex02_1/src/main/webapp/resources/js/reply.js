console.log("Reply Module......");

var replyService = (function() {
    /*등록처리*/
	function add(reply, callback, error) {
		console.log("add reply.......");

		// 추가
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
	return {add:add};
})();
//외부에서 add 함수 호출 방법 : replyService.add(객체, 콜백)