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
	$(document).ready(function() {
		// 模态框登录按钮设置点击事件
		$("#login").click(function() {
			$("#userLoginForm").submit();
		});
	});
</script>

</head>
<body>

	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<!-- 头部导航条 -->
				<nav class="navbar navbar-default navbar-fixed-top"
					role="navigation">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse"
							data-target="#bs-example-navbar-collapse-1">
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
							<%
								if (session.getAttribute("currentUser") == null) {
							%>
							<li><a data-toggle="modal" href="#myModal"
								style="margin: auto 80px auto auto;">登录/注册</a></li>
							<%
								} else {
							%>
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown" style="margin: auto 80px auto auto;">个人中心
									<span class="caret"></span>
							</a>
								<ul class="dropdown-menu" role="menu">
									<li><a
										href="${pageContext.request.contextPath }/userMain.jsp">我的主页</a></li>
									<li><a
										href="${pageContext.request.contextPath }/cartDetail?userId=${currentUser.userId}">购物车</a></li>
									<li class="divider"></li>
									<li><a href="#">退出</a></li>
								</ul></li>
							<%
								}
							%>
						</ul>
					</div>
					<!-- /.navbar-collapse -->
				</div>
				<!-- /.container-fluid --> </nav>
			</div>
		</div>

		<!-- 登录异常弹出框 -->
		<%
			if (session.getAttribute("currentUser") == null) {
		%>
		<div class="row clearfix" style="margin-top: 60px;">
			<div class="col-md-12 column">
				<div class="alert alert-warning alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<strong>Warning!</strong>&nbsp;&nbsp; <span>
						<%
							if (request.getAttribute("error") == null) {
								out.println("尚未登录！");	
							}else{
								out.println((String)request.getAttribute("error"));
							}
						%>
					</span>
				</div>
			</div>
		</div>
		<%
			}
		%>
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
					<form class="form-horizontal" role="form" id="userLoginForm"
						method="post"
						action="${pageContext.request.contextPath }/userInfo">
						<input type="hidden" id="action" name="action" value="login" />
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
</html>