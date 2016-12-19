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
	
	
<title>用户注册</title>
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/images/title.ico">

</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Suzhou University</a>
			</div>
	
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="${pageContext.request.contextPath }/index">首页</a></li>
				</ul>
	
				<form class="navbar-form navbar-left" role="search" action="index" method="post">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search" id="keyword" name="keyword">
						<input type="hidden" value="selectFood" id="action" name="action" />
					</div>
					<!-- Single button -->
					<div class="btn-group">
						<button type="submit" class="btn btn-default " >
							搜一搜 
						</button>
					</div>
				</form>
	
				<ul class="nav navbar-nav navbar-right"
					style="margin: auto 80px auto auto;">
					<li><a data-toggle="modal" href="#myModal">登录/注册</a></li>
				</ul>
			</div>
		</div>
	</nav>
<form id="logout" action="userInfo" method="post" style="display: none;">
<input type="hidden" id="action" name="action" value="logout" />
</form>
<script>
function logout(){
	if(confirm("确认退出吗？")){
		$("#logout").submit();
	}
}
</script>

	<div class="container" style="margin: 80px;">
		<div class="row clearfix">
			<div class="col-md-2 column"></div>
			<div class="col-md-8 column">
				<div class="page-header">
				  <h1 style="text-align: center;">User&nbsp;Register</h1>
				</div>
				
				<form class="form-horizontal" role="form" id="registerForm"  method="post"
							action="${pageContext.request.contextPath }/userInfo">
					
					<input type="text" name="action" id="action" value="register" style="display:none;">
					
					<div class="form-group">
						<label for="userName" class="col-sm-2 control-label">用户名：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="userName"
								id="userName" placeholder="该项必填">
						</div>
					</div>

					<div class="form-group">
						<label for="password" class="col-sm-2 control-label">密码：</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" name="password"
								id="password" placeholder="该项必填">
						</div>
					</div>

					<div class="form-group">
						<label for="userIdCard" class="col-sm-2 control-label">学号：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="userIdCard"
								id="userIdCard" placeholder="该项必填">
						</div>
					</div>

					<div class="form-group">
						<label for="userRealName" class="col-sm-2 control-label">真实姓名：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="userRealName"
								id="userRealName" placeholder="该项必填">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">性别：</label>&nbsp;&nbsp;&nbsp;
						<label class="radio-inline"> <input type="radio"
							name="sex" id="sex" value="男"> 男
						</label> 
						<label class="radio-inline"> <input type="radio"
							name="sex" id="sex" value="女"> 女
						</label>
					</div>

					<div class="form-group">
						<label for="age" class="col-sm-2 control-label">年龄：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="age" id="age"
								placeholder="这里输年龄">
						</div>
					</div>
					
					<div class="form-group">
						<label for="userTel" class="col-sm-2 control-label">联系电话：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="userTel" id="userTel"
								placeholder="该项必填">
						</div>
					</div>
					
					<div class="form-group">
						<label for="address" class="col-sm-2 control-label">送货地址：</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="address" id="address"
								placeholder="这里输送货地址">
						</div>
					</div>
				
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" class="btn btn-danger btn-lg btn-block">确认
							</button>
						</div>
					</div>

				</form>
			</div>
			
			<div class="col-md-2 column"></div>
			
		</div>
	</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" align="center">进去吃饭</h4>
				</div>
				<div class="modal-body">

					<!-- 模态框表单 -->
					<form class="form-horizontal" role="form" id="userLoginForm"
						method="post"
						action="${pageContext.request.contextPath }/userInfo">
						
						<input type="text" name="action" id="action" value="login"
							style="display: none;">
							
						<div class="form-group">
							<div class="col-sm-10" style="margin: auto auto auto 25px;">
								<input type="text" class="form-control" id="userName"
									name="userName" placeholder="在这里输账号">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-10" style="margin: auto auto auto 25px;">
								<input type="password" class="form-control" id="password"
									name="password" placeholder="在这里输密码">
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<div class="checkbox">
									<label> <input type="checkbox"> Remember me
									</label>
								</div>
							</div>
						</div>
					</form>

				</div>
				<div class="modal-footer">
					<div class="row">

						<div class="col-md-4">
							<button type="button" class="btn btn-default" id="login"
								data-dismiss="modal">登录</button>
						</div>
						<div class="col-md-3"></div>
						<div class="col-md-4">
							<a href="${pageContext.request.contextPath }/userRegister.jsp"
								class="btn btn-default">注册</a>
						</div>
						<div class="col-md-1"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

$(document).ready(function(){
	$("button.btn-block").click(function(){
		var flag = confirm("确认之后不可更改，侬想好了？");
		if(flag){
			$("#registerForm").submit();
		}
	});
});

</script>
</html>