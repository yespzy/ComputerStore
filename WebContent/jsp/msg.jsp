<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
	</head>
	<body>
		<div class="container-fluid">
			<div class="container-fluid">
			
				<div class="col-md-4">
					<img src="${pageContext.request.contextPath}/images/client/logo.gif" style="width:170px;height:67px;margin-left:-15px;"/>
				</div>
				
				<div class="col-md-5">
				
				</div>
				
				<div class="col-md-3" style="padding-top:20px">
					<ol class="list-inline">
					
						<c:if test="${empty user }">
							<li><a href="${pageContext.request.contextPath }/user?method=loginUI">登录</a></li>
							<li><a href="${pageContext.request.contextPath }/user?method=registUI">注册</a></li>
						</c:if>
						
						<c:if test="${not empty user }">
							${user.username }:你好!
							<li><a href="${pageContext.request.contextPath }/order?method=findMyOrdersByPage&pageNumber=1">我的订单</a></li>
							<li><a href="${pageContext.request.contextPath }/user?method=logout" onclick="javascript:return confirm_logout()">退出</a></li>
						</c:if>
						
						<li><a href="${pageContext.request.contextPath }/jsp/cart.jsp">购物车</a></li>
						
					</ol>
				</div>
			</div>
			
			<div class="container-fluid">
				<nav class="navbar navbar-inverse">
					<div class="container-fluid">
					
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="${pageContext.request.contextPath}/jsp/index.jsp">首页</a>
						</div>

						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav" id="c_ul"></ul>
							<form class="navbar-form navbar-right" role="search">
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Search">
								</div>
								<button type="submit" class="btn btn-default">Submit</button>
							</form>
						</div>

					</div>
				</nav>
			</div>
			
			<script type="text/javascript">
				$(function(){
					//发送ajax 查询所有分类
					$.post("${pageContext.request.contextPath}/category",{"method":"findAll"},function(obj){
						//alert(obj);
						//遍历json列表,获取每一个分类,包装成li标签,插入到ul内部
						//$.each($(obj),function(){});
						$(obj).each(function(){
							//alert(this.cname);
							$("#c_ul").append("<li><a href='${pageContext.request.contextPath}/product?method=findByPage&pageNumber=1&cid="+this.cid+"'>"+this.cname+"</a></li>");
						});
					},"json");
				})
			</script>

			<div class="container-fluid">
				<h3>${msg }</h3>
			</div>

		</div>
		<div class="container-fluid">
		
				<div style="margin-top:50px;">
					<img src="${pageContext.request.contextPath}/images/client/footer.jpg" width="100%" height="78"/>
				</div>
		
				<div style="text-align: center;margin-top: 5px;">
					<ul class="list-inline">
						<li><a>关于我们</a></li>
						<li><a>联系我们</a></li>
						<li><a>配送方式</a></li>
						<li><a>服务声明</a></li>
					</ul>
				</div>
				
				<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
					Copyright &copy; 米奇商城 版权所有
				</div>
		</div>

	</body>

</html>