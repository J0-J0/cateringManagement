<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<input id="merchantId" type="text" style="display:none;" value="${currentMerchant.merchantId }">
	<input id="status" type="text" style="display:none;" value="${orderList[0].status }">
	
	
	<div id="orderListDIV">
		<c:forEach var="merchantOrder" items="${orderList }">
			<div class="panel panel-default" style="margin: 20px auto auto auto;">
				<div class="panel-heading">
					<div class="checkbox-inline" style="margin: auto auto 15px auto;">
						<label><input type="checkbox"></label>
					</div>
					<span>${merchantOrder.addTime_}</span>
					<span style="margin-left: 40px;">
						<strong>学号 : </strong>
						<font id="merchantName">${merchantOrder.userIdCard }</font>
					</span> 
					<span style="margin-left: 40px;">
						<strong>总金额 : </strong>
						<font id="sum">${merchantOrder.sum }</font>
					</span>
					<button type="button" class="close" id="${merchantOrder.orderId }">
						<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
					</button>
				</div>
	
				<div class="panel-body">
					<table>
						<c:forEach var="food" items="${merchantOrder.foodList }">
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
								<button type="button" class="btn btn-primary btn-sm" id="${merchantOrder.orderId }"
									onclick="takeOrder()">接了</button>
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
				</span> 
				<input type="text" class="form-control" value="1"  id="currentPage" />
				 <span class="input-group-btn">
					<button class="btn btn-default" type="button" id="right">下一页
						<span class="glyphicon glyphicon-chevron-right"></span>
					</button>
				</span>
			</div>
		</div>
		<div class="col-lg-4">
			<div style="margin-top: 6px; font-size: 18px;">
				共<span>${totalPages}</span>页
			</div>
		</div>
		<div class="col-lg-4">
			<div class="input-group">
				<input type="text" class="form-control" value="" /> <span
					class="input-group-btn">
					<button class="btn btn-default" type="button" id="Go">Go!</button>
				</span>
			</div>
		</div>
	</div>
	
	<form id="nextPage" style="display:none;"action="orderList" method="post">
		<input type="hidden" id="id" name="id" value="${currentMerchant.merchantId}" />
		<input type="hidden" id="status" name="status" value="0" />
		<input type="hidden" id="flag" name="flag" value="false" />
		<input type="hidden" id="page" name="page" value="" />
	</form>
</body>
<script type="text/javascript">

function takeOrder(){
	var srcElement = $(window.event.srcElement);
	var order = srcElement.parent().parent().parent().parent().parent().parent();
	$.ajax({
		url: "alterOrder",
		type: "POST",
		data: {
			action: "updateStatus",
			orderId: srcElement.attr("id"),
			status: 1
		}
	}).done(function(data, textStatus, jqXHR){
		order.css("display", "none");
	});
}

function deleteOrder(){
	var srcElement = $(window.event.srcElement);
	var order = srcElement.parent().parent();
	$.ajax({
		url: "alterOrder",
		type: "POST",
		data: {
			action: "updateStatus",
			orderId: srcElement.attr("id"),
			status: 1
		}
	}).done(function(data, textStatus, jqXHR){
		order.css("display", "none");
	});
}

$(document).ready(function() {				// keep it simple and stupid !
	// 左钮 
	$("#left").click(function() {
		var currentPage = $("#currentPage").val();
		// 检查是否可以跳转 
		if (currentPage > 1) {
			currentPage = currentPage - 1;    					// 向左 
			$("#currentPage").val(currentPage);				// 修改当前页码
			$("#page").val(currentPage);								// 给隐藏表单赋值
			$("#nextPage").submit();									// 表单提交
		}
	});
	
	// 跳转钮 
	$("#right").click(function() {
		var currentPage = $("#currentPage").val();
		if (currentPage < $("#totalPages").text()) {		// 向右 
			currentPage = currentPage - 1 + 2;    				// 修改当前页码
			$("#currentPage").val(currentPage);				
			$("#page").val(currentPage);								// 给隐藏表单赋值
			$("#nextPage").submit();									// 表单提交
		}
	});
	
	// 右钮 
	$("#Go").click(function() {
		var Go = $("#Go").val();
		// 检查是否可以跳转 
		if (Go>= 1 && Go<= $("#totalPages").text()) {
			$("#currentPage").val(Go);
			$("#page").val(currentPage);								// 给隐藏表单赋值
			$("#nextPage").submit();									// 表单提交
		}
	});
});

	
</script>
</html>