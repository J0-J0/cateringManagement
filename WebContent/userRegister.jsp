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

<script type="text/javascript">

function confirmSubmit(){
	var flag = confirm("确认之后不可更改，侬想好了？");
	if(flag){
		var registerForm = document.getElementById("registerForm");
		registerForm.submit();
	}
}

</script>

</head>
<body>


	<div class="container">
		<div class="row clearfix">
			
			<div class="col-md-2 column"></div>
		
			<div class="col-md-8 column">
				<div class="page-header">
				  <h1 style="text-align: center;">User&nbsp;Register</h1>
				</div>
				
				<form class="form-horizontal" role="form" id="registerForm"  method="post"
							action="${pageContext.request.contextPath }/userInfoUpdate">

					<div class="form-group">
						<label for="userName" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="userName"
								id="userName" placeholder="这里输用户名">
						</div>
					</div>

					<div class="form-group">
						<label for="password" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" name="password"
								id="password" placeholder="这里输密码">
						</div>
					</div>

					<div class="form-group">
						<label for="userIdCard" class="col-sm-2 control-label">学号</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="userIdCard"
								id="userIdCard" placeholder="这里输学号">
						</div>
					</div>

					<div class="form-group">
						<label for="userRealName" class="col-sm-2 control-label">真实姓名</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="userRealName"
								id="userRealName" placeholder="这里输真实姓名">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">性别</label>&nbsp;&nbsp;&nbsp;
						<label class="radio-inline"> <input type="radio"
							name="sex" id="sex" value="男"> 男
						</label> 
						<label class="radio-inline"> <input type="radio"
							name="sex" id="sex" value="option3"> 女
						</label>
					</div>

					<div class="form-group">
						<label for="age" class="col-sm-2 control-label">年龄</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="age" id="age"
								placeholder="这里输年龄">
						</div>
					</div>
					
					<div class="form-group">
						<label for="userTel" class="col-sm-2 control-label">联系电话</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="userTel" id="userTel"
								placeholder="这里输联系电话">
						</div>
					</div>
					
					<div class="form-group">
						<label for="address" class="col-sm-2 control-label">送货地址</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="address" id="address"
								placeholder="这里输送货地址">
						</div>
					</div>
				
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" class="btn btn-danger btn-lg btn-block" onclick="confirmSubmit()">确认
							</button>
						</div>
					</div>

				</form>
			</div>
			
			<div class="col-md-2 column"></div>
			
		</div>
	</div>

</body>
</html>