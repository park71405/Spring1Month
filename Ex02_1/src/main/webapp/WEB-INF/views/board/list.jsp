<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="../includes/header.jsp"%>

<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">Tables</h1>

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h4 class="m-0 font-weight-bold text-primary">
				List <a href="/board/register"
					class="btn btn-primary btn-sm float-right">글쓰기</a>
			</h4>
		</div>
		<div class="card-body">
			<div class="table-responsive">
				<table class="table table-bordered" width="100%" cellspacing="0">

					<tr>
						<th>#번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>수정일</th>
					</tr>

					<c:forEach items="${list}" var="board">
						<tr>
							<td><c:out value="${board.bno}" /></td>
							<td>
								<a class="move" href="<c:out value='${board.bno}' />">
									<c:out value="${board.title}" />
								</a>
							</td>
							<td><c:out value="${board.writer }"></c:out></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd"
									value="${board.regdate}" /></td>
							<td><fmt:formatDate pattern="yyyy-MM-dd"
									value="${board.updateDate}" /></td>
						</tr>
					</c:forEach>
				</table>

				<!-- 페이징 처리 -->
				<div class="float-right">
					<ul class="pagination">
						<c:if test="${pageMaker.prev }">
							<li class="page-item previous"><a class="page-link"
								href="${pageMaker.startPage -1 }">Previous</a></li>
						</c:if>

						<c:forEach var="num" begin="${pageMaker.startPage }" end="${pageMaker.endPage }">
							<li class="page-item ${pageMaker.cri.pageNum == num ? 'active':''}">
								<a class="page-link" href="${num }">${num }</a>
							</li>
						</c:forEach>

						<c:if test="${pageMaker.next }">
							<li class="page-item next"><a class="page-link"
								href="${pageMaker.endPage +1 }">Next</a></li>
						</c:if>
					</ul>
				</div>

				<!-- 페이지 클릭하면 동작하는 부분 -->
				<form id='actionForm' action="/board/list" method='get'>
					<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
					<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
					<input type="hidden" name="type" value="${pageMaker.cri.type}">  
				    <input type="hidden" name="keyword" value="${pageMaker.cri.keyword}"> 
				</form>  
				
				<!-- 검색 창 -->
				<div class="row" style="clear:right;width:500px;margin:auto">
					<div class="col-lg-12">
						<form id="searchForm" action="/board/list" method="get">
							<select name="type">
								<option value="">전체보기</option>
								<option value="T">제목</option>
								<option value="C">내용</option>
								<option value="W">작성자</option>
								<option value="TC">제목과 내용</option>
							</select>
							<input type="text" name="keyword" value="${pageMaker.cri.keyword}"/>
							<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
							<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
							<button class="btn btn-primary btn-sm">Search</button>
						</form>
					</div>
				</div>


				<!-- Modal 추가 -->
				<div class="modal" id="myModal">
					<div class="modal-dialog">
						<div class="modal-content">

							<!-- Modal Header -->
							<div class="modal-header">
								<h4 class="modal-title">알림</h4>
								<button type="button" class="close" data-dismiss="modal">&times;</button>
							</div>

							<!-- Modal body -->
							<div class="modal-body">처리가 완료되었습니다.</div>

							<!-- Modal footer -->
							<div class="modal-footer">
								<button type="button" class="btn btn-danger"
									data-dismiss="modal">닫기</button>
							</div>

						</div>
					</div>
				</div>


			</div>
		</div>
	</div>
	<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->

<script>
	$(function() {
		let result = '<c:out value="${result}" />';
		checkModal(result);
		history.replaceState({}, null, null); //뒤로가기 시 과거 자신이 가진 모든 데이터 활용해 가시 게시물의 등록 결과를 확인함 -> history 객체로 처리한 기능은 다시 표시 안하도록함

		function checkModal(result) {
			if (result === '' || history.state) {
				return;
			}
			if (parseInt(result) > 0) {
				$(".modal-body").html(
						"게시글 " + parseInt(result) + " 번이 등록되었습니다.");
			}

			$("#myModal").modal("show");
		}

	
		$("#regBtn").on("click", function() {
			selef.location = "/board/register";
		});

		/* 페이징 처리 */
		let actionForm = $("#actionForm");

		$(".page-item a").on("click", function(e) {
			e.preventDefault();

			actionForm.find("input[name='pageNum']").val($(this).attr("href"));

			actionForm.submit();
		});
		
		$(".move").on("click", function(e){
			e.preventDefault();
			actionForm.append("<input type='hidden' name='bno' value='" + $(this).attr("href") + "'>");
			actionForm.attr("action", "/board/get");
			actionForm.submit();
		});
		
		let searchForm = $("#searchForm");
		
		$("#searchForm button").on("click",function(e) {
		    // 화면에서 키워드가 없다면 검색을 하지 않도록 제어
			if (!searchForm.find("option:selected").val()) {
				alert("검색종류를 선택하세요");
				return false;
			}

			if (!searchForm.find("input[name='keyword']").val()) {
				alert("키워드를 입력하세요");
				return false;
			}

		    // 페이지 번호를 1로 처리
			searchForm.find("input[name='pageNum']").val("1");

		    // 폼 태그의 전송을 막음
			e.preventDefault();

			searchForm.submit();

		});

	});
</script>

<%@ include file="../includes/footer.jsp"%>
