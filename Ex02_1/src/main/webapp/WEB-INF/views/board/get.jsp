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
				<button data-oper="modify" class="btn btn-primary btn-sm">Modify</button> <!-- data-oper : hidden으로 태그를 숨길 필요없이 데이터 저장  -->
				<button data-oper="list" class="btn btn-primary btn-sm">List</button>
			</form>
			
		</div>

	</div>
	<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->

<script>
	$(document).ready(function(){
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