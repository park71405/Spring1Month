<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<%@include file="../includes/header.jsp"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Read</h1>
	</div>
</div>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Read Page</div>

			<!-- /.panel-heading -->
			<div class="panel-body">
			
				<div class="form-group">
					<label>Bno</label> 
					<input class="form-control" name="bno" value='${board.bno}' readonly="readonly">
				</div>
			
				<div class="form-group">
					<label>Title</label> 
					<input class="form-control" name="title" value="${board.title}" readonly="readonly">
				</div>
					
				<div class="form-group">
					<label>Text area</label>
					<textarea class="form-control" rows="3" name="content" readonly="readonly"><c:out value="${board.content}" /></textarea>
				</div>
					
				<div class="form-group">
					<label>Writer</label> 
					<input class="form-control" name="writer" readonly="readonly" value="${board.writer}">
				</div>
					
				<button data-oper="modify" class="btn btn-default" onclick="location.href='/board/modify?bno=${board.bno}'">Modify</button>
				<button data-oper="list" class="btn btn-default" onclick="location.href='/board/list'">List</button>
				
			</div>
			<!-- /.panel-body -->

		</div>
	</div>
</div>

<%@include file="../includes/footer.jsp"%>