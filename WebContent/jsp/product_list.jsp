<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
				width: 100%;
			}
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>
	<body>
		<%@include file="/jsp/head.jsp" %>
		
		<div class="row" style="width:1210px;margin:0 auto;">
		
			<div class="col-md-12">
				<ol class="breadcrumb">
				    <li>
				       <img src="${pageContext.request.contextPath}/images/client/warm.png" height="20"  style="display: inline-block;"/>
				    </li>
					<li>
					<marquee width="70%" behavior="scroll" direction="right">
						<a href="">【商品声明】绝无假货，假一赔十！</a>
						&nbsp;&nbsp;
						<a href="">【本店声明】诚信经营，谋求合作！</a>
						&nbsp;&nbsp;
						<a href="">【全场促销】全场5折!全场5折！全场5折！</a>
					</marquee>
					</li>
				</ol>
			</div>
			
			<c:forEach items="${pb.data}" var="p">
				<div class="col-md-2">
					<a href="${pageContext.request.contextPath }/product?method=getById&pid=${p.pid}">
						<img src="${pageContext.request.contextPath}/${p.pimage}" width="170" height="170" style="display: inline-block;">
					</a>
					<p><a href="${pageContext.request.contextPath }/product?method=getById&pid=${p.pid}" style='color:green'>${fn:substring(p.pname,0,10) }..</a></p>
					<p><font color="#FF0000">商城价：&yen;${p.shop_price }</font></p>
				</div>
			</c:forEach>
			
		</div>
		<div style="width:380px;margin:0 auto;margin-top:50px;">
			<ul class="pagination" style="text-align:center; margin-top:10px;">
				
				<c:if test="${pb.pageNumber == 1 }">
					<li class="disabled">
						<a href="javascript:void(0)" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>
				
				<c:if test="${pb.pageNumber != 1 }">
					<li>
						<a href="${pageContext.request.contextPath }/product?method=findByPage&pageNumber=${pb.pageNumber-1}&cid=${param.cid}" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>
				
				<c:forEach begin="1" end="${pb.totalPage }" var = "n">
					<c:if test="${pb.pageNumber == n }">
						<li class="active"><a href="javascript:void(0)">${n }</a></li>
					</c:if>
					<c:if test="${pb.pageNumber != n }">
						<li><a href="${pageContext.request.contextPath }/product?method=findByPage&cid=${param.cid}&pageNumber=${n}">${n }</a></li>
					</c:if>
				</c:forEach>
				
				<c:if test="${pb.pageNumber == pb.totalPage }">
					<li  class="disabled">
						<a href="javascript:void(0)" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
				
				<c:if test="${pb.pageNumber != pb.totalPage }">
					<li>
						<a href="${pageContext.request.contextPath }/product?method=findByPage&cid=${param.cid}&pageNumber=${pb.pageNumber+1}" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
				
			</ul>
		</div>

		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/images/client/footer.jpg" width="100%" height="78" />
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

	</body>
</html>