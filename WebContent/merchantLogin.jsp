<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap3/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script
	src="${pageContext.request.contextPath}/bootstrap3/js/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script
	src="${pageContext.request.contextPath }/bootstrap3/js/bootstrap.min.js"></script>

<title>商家登录</title>
<script type="text/javascript">
function resetValue(){
	var merchantName = document.getElementById("merchantName");
	var password = document.getElementById("password");
	merchantName.value = "";
	password.value = "";
}
</script>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-2 column">
				<img src="${pageContext.request.contextPath }/images/logo.png" />
			</div>
			<div class="col-md-10 column">
				<h2 style="margin: 15px auto auto 0px;">Merchant Login</h2>
			</div>
		</div>
	</div>

	<div style="margin: 10px;">
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-8 column" style="margin: 30px auto 60px auto;">
					<img class="img-thumbnail"
						src="${pageContext.request.contextPath }/images/merchantLogin.png">
				</div>
				<div class="col-md-4 column">
					
					<blockquote style="margin:40px auto auto auto;">
						<h4>学生止步</h4>
						<p>本页面仅开放给商家，学生登录当心我封你账号！</p>
						<footer>jojo</footer>
					</blockquote>
					
					<form class="form-horizontal" role="form" action = "${pageContext.request.contextPath }/merchantLogin"
						style="margin: 40px auto 60px 10px;" method="post">
						<div class="form-group">
							<div class="col-sm-10"><font color="red">${error }</font>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-10">
								<input type="text" class="form-control" id="merchantName" name = "merchantName"
									placeholder="在这里输用户名">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-10">
								<input type="password" class="form-control" id="password" name ="password"  
									placeholder="在这里输密码">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-6">
								<button type="submit" class="btn btn-primary" style="margin:auto auto auto 40px;">登录</button>
							</div>
							<div class="col-sm-6">
								<button class="btn btn-default" onclick="resetValue()">重置</button>
							</div>
						</div>
					</form>

				</div>
			</div>
		</div>
	</div>
</body>
</html>