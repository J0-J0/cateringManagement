<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
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
				<a class="navbar-brand" href="#"> Brand</a>
			</div>
	
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="${pageContext.request.contextPath }/index">首页</a></li>
					<li><a href="#">Link</a></li>
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
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">个人中心 <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a
								href="${pageContext.request.contextPath }/userMain.jsp">我的主页</a></li>
							<li><a 
								href="${pageContext.request.contextPath }/cartDetail?userId=${currentUser.userId}">购物车</a></li>
							<li class="divider"></li>
							<li><a href="#"onclick="logout()">退出</a></li>
						</ul></li>
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


	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="page-header">
					<h1>
						你是在找这个吗 <small>——by jojo</small>
					</h1>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="row clearfix">
						<c:forEach var="food" items="${foodList}">
							<div class="col-md-3 column">
								<div class="thumbnail">
									<a
										href="${pageContext.request.contextPath }/foodDetail?foodId=${food.foodId}&merchantName=${merchantName}">
										<img data-src="holder.js/300x300" alt="300x300"
										style="height: 200px; width: 200px;">
									</a>
									<div class="caption">

										<h3 style="display: inline;" id="foodFoodName">${food.foodName }</h3>
										<h3 style="display: inline;">
											&nbsp;&nbsp;&nbsp;<small>￥：</small><small id="foodFoodPrice">${food.foodPrice }</small>
										</h3>

										<p>${food.description }</p>
										<p>剩余数量：${food.num }</p>
										<p>
											<a id="${food.foodId}" class="btn btn-primary" role="button"
												 onclick="cart()">加购物车</a> <a
												id="${food.foodId}" class="btn btn-default" role="button"
												style="margin-left: 10px;">放收藏夹</a>
										</p>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
			</div>
		</div>
	</div>
</body>
</html>