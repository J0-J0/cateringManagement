<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

	<div class="container">
		<div class="row clearfix">
			<div class="col-md-2 column"></div>
			<div class="col-md-8 column">
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation"><a href="#" onclick="showPositive()">好评</a></li>
					<li role="presentation"><a href="#" onclick="showNegative()">差评</a></li>
				</ul>
				
				<div id="positiveDIV">
					<div id="positive">
						<c:forEach var="comment"  items="${positiveList }">
							<blockquote>
								<p>${comment.comment }</p>
								<footer>
									<fmt:formatDate type="date" value="${comment.addTime }" />
									<cite>${comment.userName }</cite>
								</footer>
							</blockquote>
						</c:forEach>
					</div>

					<!-- 分页按钮 -->
					<div class="row clearfix" style="margin-top: 20px;">
						<div class="col-lg-4">
							<div class="input-group">
								<span class="input-group-btn">
									<button class="btn btn-default" type="button" onclick="left()" id="1">
										<span class="glyphicon glyphicon-chevron-left"></span>上一页
									</button>
								</span> 
								<input type="text" class="form-control" value="1" />
								<span class="input-group-btn">
									<button class="btn btn-default" type="button" onclick="right()" id="1">
										下一页<span class="glyphicon glyphicon-chevron-right"></span>
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
								<input type="text" class="form-control" value="" /> 
								<span class="input-group-btn">
									<button class="btn btn-default" type="button" id="1" onclick="go()">Go!</button>
								</span>
							</div>
						</div>
					</div>
				</div>

				<div id="negativeDIV" style="display:none;">
					<div id="negative">
						<c:forEach var="comment"  items="${negativeList }">
							<blockquote>
								<p>${comment.comment }</p>
								<footer> 
									<fmt:formatDate type="date" value="${comment.addTime }" /> 
									<cite>${comment.userName }</cite>
								</footer>
							</blockquote>
						</c:forEach>
					</div>
					
					<!-- 分页按钮 -->
					<div class="row clearfix" style="margin-top: 20px;">
						<div class="col-lg-4">
							<div class="input-group">
								<span class="input-group-btn">
									<button class="btn btn-default" type="button" id="0" onclick="left()">
										<span class="glyphicon glyphicon-chevron-left"></span>上一页
									</button>
								</span> <input type="text" class="form-control" value="1" /> <span
									class="input-group-btn">
									<button class="btn btn-default" type="button" id="0" onclick="right()">
										下一页<span class="glyphicon glyphicon-chevron-right"></span>
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
									<button class="btn btn-default" type="button" id="0" onclick="go()">Go!</button>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-2 column"></div>
		</div>
	</div>



</body>
<script type="text/javascript">
	function showPositive(){
		$("#negativeDIV").fadeOut();
		$("#positiveDIV").fadeIn();
	}
	function showNegative(){
		$("#positiveDIV").fadeOut();
		$("#negativeDIV").fadeIn();
	}
	
	function left(){
		var srcElement = $(window.event.srcElement);
		var currentPage = srcElement.parent().next();
		if(currentPage.val() > 1){
			$.ajax({
				url:"/alterComment",
				type:"POST",
				data:{
					page:currentPage.val()-1,
					foodId:$("#fid").val(),
					isPositive:srcElement.attr("id"),
					flag:"false"
				}
			}).done(function(data, textStatus, jqXHR){
				var commentList = $.parseJSON(data);
				fillDIV(commentList, srcElement.attr("id"));
				currentPage.val(currentPage.val()-1);
			});
		}
	}
	
	function right(){
		var srcElement = $(window.event.srcElement);
		var currentPage = srcElement.parent().prev();
		var totalPages = currentPage.parent().parent().next().children()[0].children[0].text();
		if(currentPage.val() < totalPages){
			$.ajax({
				url:"/alterComment",
				type:"POST",
				data:{
					page:currentPage.val()+1,
					foodId:$("#fid").val(),
					isPositive:srcElement.attr("id"),
					flag:"false"
				}
			}).done(function(data, textStatus, jqXHR){
				var commentList = $.parseJSON(data);
				fillDIV(commentList, srcElement.attr("id"));
				currentPage.val(currentPage.val()+1);
			});
		}
	}
	
	function go(){
		var srcElement = $(window.event.srcElement);
		var aimPage = srcElement.parent().prev().val();
		var totalPages = srcElement.parent().parent().parent().prev().children()[0].children[0].text();
		var currentPage = srcElement.parent().parent().parent().prev().prev().children()[0].children[1];
		if((aimPage < totalPages) && (aimPage > 1)){
			$.ajax({
				url:"/alterComment",
				type:"POST",
				data:{
					page:aimPage,
					foodId:$("#fid").val(),
					isPositive:srcElement.attr("id"),
					flag:"false"
				}
			}).done(function(data, textStatus, jqXHR){
				var commentList = $.parseJSON(data);
				fillDIV(commentList, srcElement.attr("id"));
				currentPage.val(aimPage);
			});
		}
	}
	
	function fillDIV(commentList, flag){
		var div = null;
		if(flag == 1){
			div = $("#positive");
		}else{
			div = $("#negative");
		}
		div.empty();
		for(var i = 0; i < commentList.length; i++){
			var node = "<blockquote>"
							+		"<p>"+commentList[i].comment+"</p>"
							+		"<footer>" +commentList[i].addTime
							+			"<cite>"+commentList[i].userName+"</cite>"
							+		"</footer>"
							+	"</blockquote>";
		div.append($(node));
		}
	}
</script>
</html>