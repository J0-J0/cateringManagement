<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
				<span><fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${userOrder.ackTime}" /></span>
				<span style="margin-left: 40px;">
					<strong>商家名称 : </strong>
					<font>${userOrder.merchantName }</font>
				</span> 
				<span style="margin-left: 40px;">
					<strong>总金额 : </strong>
					<font>${userOrder.sum }</font>
				</span>
				<button type="button" class="close">
					<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
				</button>
			</div>

			<div class="panel-body">
					<c:forEach var="food" items="${userOrder.foodList }">
						<div class="row clearfix">
							<div class="col-md-3 column">缩略图</div>
							<div class="col-md-3 column">${food.foodName }</div>
							<div class="col-md-2 column">${food.num }</div>
							<div class="col-md-2 column">${food.foodSum }</div>
							<div class="col-md-2 column">
								<button type="button" class="btn btn-warning btn-sm" id="${food.foodId }" onclick="launchModal()">尚未评价</button>
							</div>
						</div>
					</c:forEach>
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


<!-- 评论用模态框 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">我要吐槽这盘菜~</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="prepareToDelete" />
					<input type="hidden" id="isPositive" value="1" />
					<input type="hidden" id="uid" value="${currentUser.userId }" />
					<form role="form">
						<div class="form-group">
							<label class="col-sm-2 control-label">这个菜</label>&nbsp;&nbsp;&nbsp;
							<label class="radio-inline"> <input type="radio"/> 蛮好</label> 
							<label class="radio-inline"> <input type="radio" onclick="negative()"/> 垃圾</label>
						</div>
						<div class="form-group">
							<textarea class="form-control" rows="3" id="textarea"></textarea>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="addComment()">确认评价</button>
				</div>
			</div>
		</div>
	</div>



<form id="nextPage" style="display:none;"action="orderList" method="post">
	<input type="hidden" id="id" name="id" value="${currentUser.userId}" />
	<input type="hidden" id="status" name="status" value="2" />
	<input type="hidden" id="flag" name="flag" value="true" />
	<input type="hidden" id="page" name="page" value="" />
</form>
</body>
<script type="text/javascript">
function launchModal(){
	$("#prepareToDelete").val($(window.event.srcElement).attr("id"));
	$("#myModal").modal();
}
function negative(){
	$("#isPositive").val(0);  // 给它差评！ 
}
function addComment(){
	$.ajax({
		url:"alterComment",
		type:"POST",
		data:{
			action:"add",
			comment:$("#textarea").val(),
			userId:$("#uid").val(),
			foodId:$("#prepareToDelete").val(),
			isPositive:$("#isPositive").val()
		}
	}).done(function(){
		var buttonId = $("#prepareToDelete").val();
		$("#"+buttonId).remove();
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