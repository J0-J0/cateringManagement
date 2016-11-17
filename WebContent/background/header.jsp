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


<script type="text/javascript">
function verify(){
	var status = document.getElementById("status").value;
	/**
	 * view1代表登录注册按钮，view2代表我的主页，
	 *  view3代表警告框，view4代表警告框里的文字
	 */
	 
	if(status == "null"){
		var view4 = document.getElementById("view4");
		view4.innerHTML = "用户民或密码不能为空！";
		
	}else if(status == "failed"){
		var view4 = document.getElementById("view4");
		view4.innerHTML = "用户民或密码错误！";
		
	}else if(status == "success"){
		
		// 关闭警告框 
		var view3 = document.getElementById("view3");
		view3.style.display = "none";
		// 关闭登录按钮
		var view1 = document.getElementById("view1");
		view1.style.display = "none";
		// 开启我的主页按钮
		var view2 = document.getElementById("view2");
		view2.style.display = "";
	}
}

function verifyUser(){
	var form = document.getElementById("modalForm");
	form.submit();
}
</script>

</head>
<body onload = "verify()">
	
	<input type="text"  id="status" style = "display: none;" value="${status }" />
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
	<!-- 头部导航条 -->
			<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#"> Brand</a>
				</div>
		
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li class="active"><a href="#">首页</a></li>
						<li><a href="#">Link</a></li>
					</ul>
					<form class="navbar-form navbar-left" role="search">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Search">
						</div>
		
						<!-- Single button -->
						<div class="btn-group">
							<button type="button" class="btn btn-default dropdown-toggle"
								data-toggle="dropdown">
								搜一搜 <span class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#">搜店家</a></li>
								<li><a href="#">搜小菜</a></li>
							</ul>
						</div>
		
		
					</form>
					<ul class="nav navbar-nav navbar-right">
						<li id="view1"><a data-toggle="modal" 
							href="#myModal" style="margin: auto 80px auto auto;">登录/注册</a></li>
						<li id="view2" class="dropdown" style="display: none;"><a 
							href="#" class="dropdown-toggle" data-toggle="dropdown" style="margin: auto 80px auto auto;">个人中心 <span
								class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="${pageContext.request.contextPath }/userMain.jsp">我的主页</a></li>
								<li><a href="#">购物车</a></li>
								<li class="divider"></li>
								<li><a href="#">退出</a></li>
							</ul></li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid --> </nav>
		</div>
	</div>

		<!-- 登录异常弹出框 -->
		<div class="row clearfix" style="margin-top: 60px;">
			<div class="col-md-12 column">
				<div id = "view3" class="alert alert-warning alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<strong>Warning!</strong>&nbsp;&nbsp;<span id = "view4">尚未登录</span>
				</div>
				</div>
		</div>
	</div>
	<!-- 模态框，这龟孙反正是看不见得 -->
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
					<form class="form-horizontal" role="form" id="modalForm" method="post"
						action="${pageContext.request.contextPath }/userLogin">
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
							<button type="button" class="btn btn-default"
								onclick="verifyUser()" data-dismiss="modal">登录</button>
						</div>
						<div class="col-md-3"></div>
						<div class="col-md-4">
							<a href="${pageContext.request.contextPath }/userRegister.jsp" class="btn btn-default">注册</a>
						</div>
						<div class="col-md-1"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>