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

<style type="text/css">
	.center{
		text-align: center;
	}
</style>

</head>
<body>


<c:forEach var="foodType" items="${foodTypeList }">

	<div class="page-header">
	  <h3>${foodType[0].foodType}</h3>
	</div>
	
	<div class="row clearfix">
		<div class="col-md-12 column">
			<table class="table  table-bordered  table-hover">
				<thead>
					<tr>
						<th class="center">名称</th>
						<th class="center">类别</th>
						<th class="center">剩余</th>
						<th class="center">价格</th>
						<th class="center"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="food" items="${foodType}">
							<tr>
								<td class="center">${food.foodName }</td>
								<td class="center">${food.foodType }</td>
								<td class="center">${food.num }</td>
								<td class="center">${food.foodPrice }</td>
								<td class="center"><a role="button"
									class="btn btn-primary btn-xs" style="width: 40%;"
									href="${pageContext.request.contextPath }/alterFood?action=showUpdate&foodId=${food.foodId}">
										修&nbsp;改 </a></td>
							</tr>
						</c:forEach><!-- 打印单个食物 -->
				</tbody>
			</table>
		</div>
	</div>
	<!-- 打印食物类别 -->
</c:forEach>


</body>
</html>