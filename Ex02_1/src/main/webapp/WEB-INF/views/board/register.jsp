<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="../includes/header.jsp"%>

<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">Board Register</h1>

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h4 class="m-0 font-weight-bold text-primary">Register</h4>
		</div>
		<div class="card-body">
			<form role="form" action="/board/register" method="post">
				<div class="form-group">
					<label>Title</label> <input class="form-control" name="title">
				</div>
				<div class="form-group">
					<label>Content</label>
					<textarea class="form-control" rows="10" name="content"></textarea>
				</div>
				<div class="form-group">
					<label>Writer</label> <input class="form-control" name="writer">
				</div>
				<button type="submit" class="btn btn-primary btn-sm">등록</button>
				<button type="reset" class="btn btn-primary btn-sm">취소</button>
			</form>
		</div>
	</div>
	<!-- /.container-fluid -->
</div>


<!-- End of Main Content -->
<%@ include file="../includes/footer.jsp"%>