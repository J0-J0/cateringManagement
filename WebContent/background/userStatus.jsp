<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
</head>
<body>


<c:forEach var="userOrder" items="${orderList }">
						<div class="panel panel-default" style= "margin:20px auto auto auto;">
						  <div class="panel-heading">
						  	<div class="checkbox-inline" style="margin:auto auto 15px auto;">
							    <label><input type="checkbox"></label>
							 </div>
							 <span>${userOrder.addTime_}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 <span>商家名称&nbsp; : &nbsp;&nbsp;${userOrder.merchantName }</span>
							 <span>总金额&nbsp; : &nbsp;&nbsp;${userOrder.sum }</span>
							 <button type="button" class="close">
							 	<span aria-hidden="true">&times;</span>
							 	<span class="sr-only">Close</span>
							 </button>
						  </div>
						  
						  <div class="panel-body">
						    <table>
						    <c:forEach var="food" items="${userOrder.foodList }">
						    	<tr>
						    		<td width="25%">缩略图</td>
						    		<td width="50%">${food.foodName }</td>
						    		<td width="17%">${food.num }</td>
						    		<td>${food.foodSum }</td>
						    	</tr>
						    </c:forEach>
						    </table>
						  </div>
						</div>
</c:forEach>

<nav>
  <ul class="pagination">
    <li><a href="#">&laquo;</a></li>
    <li><a href="${pageContext.request.contextPath}/orderList?id=${currentUser.userId}&status=${orderList[0].status}&flag=true&page=1">1</a></li>
    <li><a href="${pageContext.request.contextPath}/orderList?id=${currentUser.userId}&status=${orderList[0].status}&flag=true&page=2">2</a></li>
    <li><a href="${pageContext.request.contextPath}/orderList?id=${currentUser.userId}&status=${orderList[0].status}&flag=true&page=3">3</a></li>
    <li><a href="${pageContext.request.contextPath}/orderList?id=${currentUser.userId}&status=${orderList[0].status}&flag=true&page=4">4</a></li>
    <li><a href="${pageContext.request.contextPath}/orderList?id=${currentUser.userId}&status=${orderList[0].status}&flag=true&page=5">5</a></li>
    <li><a href="#">&raquo;</a></li>
  </ul>
</nav>

</body>
</html>