<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix= "c" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>苏应食堂~</title>
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/images/title.ico">


<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap3/css/bootstrap.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script
	src="${pageContext.request.contextPath}/bootstrap3/js/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script
	src="${pageContext.request.contextPath }/bootstrap3/js/bootstrap.min.js"></script>



<style type="text/css">
.img-center {
	clear: both;
	display: block;
	margin: auto;
}
</style>

<script type="text/javascript">
$(document).ready(function(){
	// 模态框登录按钮设置点击事件
	$("#login").click(function(){
		$("#userLoginForm").submit();
	});
});
</script>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse"
							data-target="#bs-example-navbar-collapse-1">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="#">Suzhou University</a>
					</div>

					<div class="collapse navbar-collapse"
						id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav">
							<li class="active"><a href="${pageContext.request.contextPath }/index">首页</a></li>
						</ul>

						<form class="navbar-form navbar-left" role="search" action="index"
							method="post">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="Search"
									id="keyword" name="keyword"> <input type="hidden"
									value="selectFood" id="action" name="action" />
							</div>
							<!-- Single button -->
							<div class="btn-group">
								<button type="submit" class="btn btn-default ">搜一搜</button>
							</div>
						</form>


						<ul class="nav navbar-nav navbar-right" style="margin: auto 80px auto auto;">
							<%
								if (session.getAttribute("currentUser") == null) {
							%>
							<li><a data-toggle="modal" href="#myModal">登录/注册</a></li>
							<%
								} else {
							%>
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">个人中心 <span class="caret"></span>
							</a>
								<ul class="dropdown-menu" role="menu">
									<li><a
										href="${pageContext.request.contextPath }/userMain.jsp">我的主页</a></li>
									<li><a
										href="${pageContext.request.contextPath }/cartDetail?userId=${currentUser.userId}">购物车</a></li>
									<li class="divider"></li>
									<li><a href="#"onclick="logout()">退出</a></li>
								</ul></li>
							<%
								}
							%>
						</ul>
					</div>
				</div>
				</nav>
			</div>
		</div>
		<%
			if (session.getAttribute("currentUser") == null) {
		%>
		<div class="row clearfix" style="margin-top: 60px;">
			<div class="col-md-12 column">
				<div class="alert alert-warning alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<strong>Warning!</strong>&nbsp;&nbsp;
					<span> 
					<%
					 	if (session.getAttribute("error") != null) {
					 			out.println((String) session.getAttribute("error"));
					 		} else {
					 			out.println("尚未登录！");
					 		}
					 %>
					</span>
				</div>
			</div>
		</div>
		<%} %>
	</div>

	<!-- 具体内容 -->
	<div class="container" style = "margin-top: 30px;">
		<!-- 图片轮转 -->
		<div id="carousel-example-generic" class="carousel slide"
			data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#carousel-example-generic" data-slide-to="0"
					class="active"></li>
				<li data-target="#carousel-example-generic" data-slide-to="1"></li>
				<li data-target="#carousel-example-generic" data-slide-to="2"></li>
				<li data-target="#carousel-example-generic" data-slide-to="3"></li>
			</ol>

			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox">
			
			<!-- 这里是forEach -->
			<c:forEach var= "indexFood" items= "${indexFoodList}">
				<div class="item">
					<img src="${pageContext.request.contextPath}/${indexFood.pics[0]}"
						alt="..." style="width: 1600px; height: 500px;">
					<div class="carousel-caption">${indexFood.description}</div>
				</div>
			</c:forEach>

				
				<!-- 滚动页 -->
				<div class="item active">
					<!-- 这里放个欢迎光临好了 -->
					<img src="${pageContext.request.contextPath}/images/duck.jpg"
						alt="..." style="width: 1600px; height: 500px;">
					<div class="carousel-caption">鸭腿饭，来自沙县</div>
				</div>
				
				<div class="item">
					<img src="${pageContext.request.contextPath}/images/slide3.jpg"
						alt="..." style="width: 1600px; height: 500px;">
					<div class="carousel-caption">随便写写</div>
				</div>

				<div class="item">
					<img src="${pageContext.request.contextPath}/images/slide2.jpg"
						alt="..." style="width: 1600px; height: 500px;">
					<div class="carousel-caption">随便写写</div>
				</div>

				<div class="item">
					<img src="${pageContext.request.contextPath}/images/slide4.jpg"
						alt="..." style="width: 1600px; height: 500px;">
					<div class="carousel-caption">随便写写</div>
				</div>
			</div>

			<!-- Controls -->
			<a class="left carousel-control" href="#carousel-example-generic"
				role="button" data-slide="prev"> <span
				class="glyphicon glyphicon-chevron-left"></span> <span
				class="sr-only">Previous</span>
			</a> <a class="right carousel-control" href="#carousel-example-generic"
				role="button" data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right"></span> <span
				class="sr-only">Next</span>
			</a>
		</div>

		<!-- 商家展示 -->
		<div class="row clearfix" style="margin: 20px;">
			<c:forEach var="merchant" items="${merchantList }">
				<div class="col-md-3 column">
					<div class="thumbnail">
						<img alt="140x140" style="width: 140px; height: 140px;"
							src="${pageContext.request.contextPath }/images/Food.png"
							class="img-rounded img-center" />
						<div class="caption">
							<h2>${merchant.merchantName }</h2>
							<p>${merchant.description }</p>
							<p>
								<a class="btn btn-primary"
									href="${pageContext.request.contextPath }/merchantDetail?merchantId=${merchant.merchantId}&merchantName=${merchant.merchantName}">进去瞧瞧
									»</a>
							</p>
						</div>
					</div>
				</div>
			</c:forEach>
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
</html>