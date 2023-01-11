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

	<!-- form 태그 추가 -->
	<form role="form" action="/board/modify" method="post">
	
		<input type="hidden" name="pageNum" value="${cri.pageNum}">
		<input type="hidden" name="amount" value="${cri.amount}"> 
		<input type="hidden" name="type" value="${cri.type}">
		<input type="hidden" name="keyword" value="${cri.keyword}">

		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<h4 class="m-0 font-weight-bold text-primary">Modify</h4>
			</div>
			<div class="card-body">
				<div class="form-group">
					<label>Bno</label> <input class="form-control"
						value="${board.bno }" name="bno" readonly>
				</div>
				<div class="form-group">
					<label>Title</label> <input class="form-control"
						value="${board.title }" name="title">
				</div>
				<div class="form-group">
					<label>Content</label>
					<textarea class="form-control" rows="10" name="content">${board.content }</textarea>
				</div>
				<div class="form-group">
					<label>Writer</label> <input class="form-control"
						value="${board.writer }" name="writer" readonly>
				</div>

				<!-- 수정 -->
				<button type="submit" data-oper="modify"
					class="btn btn-primary btn-sm">Modify</button>
				<button type="submit" data-oper="remove"
					class="btn btn-primary btn-sm">Remove</button>
				<button type="submit" data-oper="list"
					class="btn btn-primary btn-sm">List</button>
			</div>
		</div>
	</form>
	<!-- /.container-fluid -->
</div>
<!-- End of Main Content -->

<script>
	$(document).ready(function(){
		
		let formObj = $("form");
		
		$('button').on('click', function(e){
			e.preventDefault(); //전송 막기
			
			let operation = $(this).data("oper"); //data-oper 속성값 구하기
			console.log(operation);
			
			if(operation === 'remove'){
				formObj.attr("action", "/board/remove"); //action 변경
			}else if(operation === 'list'){
				
				//목록으로 이동
				formObj.attr("action", "/board/list").attr("method", "get");
				
				let pageNumTag = $("input[name='pageNum']").clone();
				let amountTag = $("input[name='amount']").clone();
				let keywordTag = $("input[name='keyword']").clone();
				let typeTag = $("input[name='type']").clone();
				
				formObj.empty();
				
				formObj.append(pageNumTag);
				formObj.append(amountTag);
				formObj.append(keywordTag);
				formObj.append(typeTag);
				
			}
			
			formObj.submit(); //폼 데이터 전송
		});
	});
</script>

<%@ include file="../includes/footer.jsp"%>