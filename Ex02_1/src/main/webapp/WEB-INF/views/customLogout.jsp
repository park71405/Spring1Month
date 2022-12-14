<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>SB Admin 2 - Logout</title>

<!-- Custom fonts for this template-->
<link href="/resources/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="/resources/css/sb-admin-2.min.css" rel="stylesheet">

<style>
.col-md-9 {
	flex: 0 0 700px;
}
</style>

</head>
<body class="bg-gradient-primary">

	<!-- Outer Row -->
	<div class="row justify-content-center">
		<div class="col-xl-10 col-md-9">
			<div class="card o-hidden border-0 shadow-lg my-5">
				<div class="card-body">
					<!-- Nested Row within Card Body -->
					<div class="row">
						<div class="col-lg-12">
							<div class="p-5">
								<div class="text-center">
									<h1 class="h4 text-gray-900 mb-4">Logout!</h1>
								</div>

								<form class="user" action="/customLogout" method='post'>
									<input type="hidden" name="${_csrf.parameterName }"
										value="${_csrf.token }" />
									<button class="btn btn-primary btn-user btn-block">Logout</button>
									<a href="/board/list"
										class="btn btn-primary btn-user btn-block">취소</a>
								</form>
								<div>
									<c:out value="${error}" />
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript-->
	<script src="/resources/vendor/jquery/jquery.min.js"></script>
	<script src="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="/resources/vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="/resources/js/sb-admin-2.min.js"></script>

</body>
</html>