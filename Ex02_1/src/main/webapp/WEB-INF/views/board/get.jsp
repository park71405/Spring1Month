<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="../includes/header.jsp"%>

<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">Board</h1>

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h4 class="m-0 font-weight-bold text-primary">Read</h4>
		</div>
		<div class="card-body">
			<div class="form-group">
				<label>Bno</label> <input class="form-control" value="${board.bno }"
					name="bno" readonly>
			</div>
			<div class="form-group">
				<label>Title</label> <input class="form-control"
					value="${board.title }" name="title" readonly>
			</div>
			<div class="form-group">
				<label>Content</label>
				<textarea class="form-control" rows="10" name="content" readonly>${board.content }</textarea>
			</div>
			<div class="form-group">
				<label>Writer</label> <input class="form-control"
					value="${board.writer }" name="writer" readonly>
			</div>
			<!-- <a data-oper="modify" class="btn btn-primary btn-sm" href="/board/modify?bno=${board.bno }"> Modify </a> 
			<a data-oper="list" class="btn btn-primary btn-sm" href="/board/list">List</a> -->
			
			<form id="operForm" action="/board/modify" method="get">
				<input type="hidden" id="bno" name="bno" value="${board.bno}">
				<input type="hidden" name="pageNum" value="${cri.pageNum}">
				<input type="hidden" name="amount" value="${cri.amount}">
				<input type="hidden" name="type" value="${cri.type}">
				<input type="hidden" name="keyword" value="${cri.keyword}">
				<button data-oper="modify" class="btn btn-primary btn-sm">Modify</button> <!-- data-oper : hidden으로 태그를 숨길 필요없이 데이터 저장  -->
				<button data-oper="list" class="btn btn-primary btn-sm">List</button>
			</form>
			
		</div>
		
		<!-- 댓글 처리 -->
		<div class="card shadow mb-4">
			<div class="card-header py-3">
			  <i class="fa fa-comments fa-fw"></i> Reply
			  <button id="addReplyBtn" class="btn btn-primary btn-sm float-right">New Reply</button>
			</div>
		</div>

	</div>
		<!-- /.container-fluid -->
	<div class="modal" id="myModal">
		<div class="modal-dialog">
	   		<div class="modal-content">
				
				<!-- Modal Header -->
	      		<div class="modal-header">
	        		<h4 class="modal-title">REPLY MODAL</h4>
	        		<button type="button" class="close" data-dismiss="modal">&times;</button>
	      		</div>
	
	      		<!-- Modal body -->
	      		<div class="modal-body">
	      			<div class="form-group">
		    			<label>Reply</label>
		      			<input class="form-control" name="reply" value="New Reply!!!">
		    		</div>
		    		<div class="form-group">
		    			<label>Replyer</label>
		      			<input class="form-control" name="replyer" value="Replyer">
					</div>
		    		<div class="form-group">
		    			<label>Reply Date</label>
		    			<input class="form-control" name="replyDate" value=''>
		    		</div>  
				</div>          
	
	      		<!-- Modal footer -->
	      		<div class="modal-footer">
	        		<button id="modalModBtn" type="button" class="btn btn-warning">Modify</button>
	        		<button id="modalRemoveBtn" type="button" class="btn btn-danger">Remove</button>
	        		<button id="modalRegisterBtn" type="button" class="btn btn-primary">Register</button>
	        		<button id="modalCloseBtn" type="button" class="btn btn-default">Close</button>
	      		</div>
	    	</div>
	  	</div>
	</div>

</div>
<!-- End of Main Content -->

<script src="/resources/js/reply.js"></script> <!-- 댓글 js -->

<script>
	$(document).ready(function(){
		
		let bnoValue = "${board.bno}";
		
		let modal = $("#myModal");
		let modalInputReply = modal.find("input[name='reply']");
		let modalInputReplyer = modal.find("input[name='replyer']");
		let modalInputReplyDate = modal.find("input[name='replyDate']");
		
		let modalModBtn = $("#modalModBtn");	//수정 버튼
		let modalRemoveBtn = $("#modalRemoveBtn");	//삭제버튼
		let modalRegisterBtn = $("#modalRegisterBtn ");	//등록버튼
		
		// new reply 버튼 클릭시 모달창 띄우기
		$("#addReplyBtn").on("click", function(e) {
			modal.find("input").val("");  //입력 항목 초기화
			modalInputReplyDate.closest("div").hide();  //날짜 숨기기
			modal.find("button[id != 'modalCloseBtn']").hide();  //닫기버튼만 보이기
			
			modalRegisterBtn.show();  //register 버튼 보이기
			$("#myModal").modal("show");  //modal 창 보이기
		});
		
		/* Register 버튼 클릭 시 댓글 등록 */
		modalRegisterBtn.on("click", function(e){
			var reply = {
					reply: modalInputReply.val(),
					replyer: modalInputReplyer.val(),
					bno: bnoValue
			};
			
			// 댓글 등록 요청
			replyService.add(reply, function(result){
				alert(result);  //결과 메세지 출력
				modal.find("input").val("");  //입력 항목 초기화
				modal.modal("hide");  //모달 창 닫기
			});
		});
		
		let operForm = $("#operForm");
		
		$("button[data-oper='modify']").on("click", function(e){
			operForm.attr("action", "/board/modify").submit();
		});
		
		$("button[data-oper='list']").on('click', function(e){
			operForm.find("#bno").remove();
			operForm.attr("action", "/board/list")
			operForm.submit();
		});
	});
</script>

<%@ include file="../includes/footer.jsp"%>