<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<!-- 给ajax请求做准备 -->
<input id="userId" type="text" style="display:none;" value="${currentUser.userId }">
<input id="status" type="text" style="display:none;" value="${orderList[0].status }">

<div id="orderListDIV">
	<c:forEach var="userOrder" items="${orderList }">
		<div class="panel panel-default" style="margin: 20px auto auto auto;">
			<div class="panel-heading">
				<div class="checkbox-inline" style="margin: auto auto 15px auto;">
					<label><input type="checkbox"></label>
				</div>
				<span>${userOrder.addTime_}</span>
				<span style="margin-left: 40px;">
					<strong>商家名称 : </strong>
					<font id="merchantName">${userOrder.merchantName }</font>
				</span> 
				<span style="margin-left: 40px;">
					<strong>总金额 : </strong>
					<font id="sum">${userOrder.sum }</font>
				</span>
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
					<tr style="margin-top:5px;">
							<td></td>
							<td></td>
							<td></td>
							<td align="right">
								<button type="button" class="btn btn-primary btn-sm" id="${userOrder.orderId }"
									onclick="takeOrder()">确认收货</button>
							</td>
					</tr>
				</table>
			</div>
		</div>
	</c:forEach>
</div>


	<div class="row clearfix" style="margin-top: 20px;">
		<div class="col-lg-4">
			<div class="input-group">
				<span class="input-group-btn">
					<button class="btn btn-default" type="button" id="left">
						<span class="glyphicon glyphicon-chevron-left"></span>上一页
					</button>
				</span> <input type="text" class="form-control" value="1" id="currentPage" /> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button" id="right">
						下一页<span class="glyphicon glyphicon-chevron-right"></span>
					</button>
				</span>
			</div>
		</div>
		<div class="col-lg-4">
			<div style="margin-top: 6px; font-size: 18px;">
				共<span id="totalPages">${totalPages}</span>页
			</div>
		</div>
		<div class="col-lg-4">
			<div class="input-group">
				<input type="text" class="form-control"  id="Go"/> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button">Go!</button>
				</span>
			</div>
		</div>
	</div>


<form id="nextPage" style="display:none;"action="orderList" method="post">
	<input type="hidden" id="id" name="id" value="${currentUser.userId}" />
	<input type="hidden" id="status" name="status" value="1" />
	<input type="hidden" id="flag" name="flag" value="true" />
	<input type="hidden" id="page" name="page" value="" />
</form>
</body>
<script type="text/javascript">

	function takeOrder() {
		var srcElement = $(window.event.srcElement);
		var order = srcElement.parent().parent().parent().parent().parent()
				.parent();

		if (confirm("想好要确认收货了？")) {
			$.ajax({
				url : "alterOrder",
				type : "POST",
				data : {
					action : "updateStatus",
					orderId : srcElement.attr("id"),
					status : 2
				}
			}).done(function(data, textStatus, jqXHR) {
				order.css("display", "none");
			});
		}
	}

	$(document).ready(function() { // keep it simple and stupid !
		// 左钮 
		$("#left").click(function() {
			var currentPage = $("#currentPage").val();
			// 检查是否可以跳转 
			if (currentPage > 1) {
				currentPage = currentPage - 1; // 向左 
				$("#currentPage").val(currentPage); // 修改当前页码
				$("#page").val(currentPage); // 给隐藏表单赋值
				$("#nextPage").submit(); // 表单提交
			}
		});

		$("#right").click(function() {
			var currentPage = $("#currentPage").val();
			if (currentPage < $("#totalPages").text()) { // 向右 
				currentPage = currentPage - 1 + 2; // 修改当前页码
				$("#currentPage").val(currentPage);
				$("#page").val(currentPage); // 给隐藏表单赋值
				$("#nextPage").submit(); // 表单提交
			}
		});

		$("#Go").click(function() {
			var Go = $("#Go").val();
			// 检查是否可以跳转 
			if (Go >= 1 && Go <= $("#totalPages").text()) {
				$("#currentPage").val(Go);
				$("#page").val(currentPage); // 给隐藏表单赋值
				$("#nextPage").submit(); // 表单提交
			}
		});
	});
</script>
</html>