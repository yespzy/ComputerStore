<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			}
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>
	   <%@include file="/jsp/head.jsp" %>
	   
		<div class="container">
		
		    <c:if test="${empty pb || empty pb.data }">
			   <h3>订单为空~~</h3>
		    </c:if>
		    
		    <c:if test="${not empty pb.data}">
				<div class="row">
					<div style="margin:0 auto; margin-top:10px;width:950px;">
						<strong>我的订单</strong>
						<table class="table table-bordered">
							<c:forEach items="${pb.data }" var="o">
								<tbody>
								
									<tr class="success">
										<th colspan="2">订单编号:${o.oid } </th>
										
										<th colspan="1">
										    <table>
				                                <tr>
					                                <td>
					                                    <c:if test="${o.state == 0 }"><a href="${pageContext.request.contextPath }/order?method=getById&oid=${o.oid}">去付款</a></c:if>
											            <c:if test="${o.state == 1 }">已付款</c:if>
											            <c:if test="${o.state == 2 }"><a href="${pageContext.request.contextPath }/order?method=updatefinishOrderStateById&oid=${o.oid}">确认收货</a></c:if>
											            <c:if test="${o.state == 3 }">已完成</c:if>
					                                </td>
					                                <td>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="deleteFrom('${o.oid}')">删除</a></td>
				                                </tr>
			                                </table>
										</th>
										
										<th colspan="2">金额:${o.total }元 </th>
									</tr>
									
									<tr class="warning">
										<th>图片</th>
										<th>商品</th>
										<th>价格</th>
										<th>数量</th>
										<th>小计</th>
									</tr>
									
									<c:forEach items="${o.items}" var="oi">
										<tr class="active">
										
											<td width="60" width="40%">
												<input type="hidden" name="id" value="22">
												<img src="${pageContext.request.contextPath}/${oi.product.pimage}" width="70" height="60">
											</td>
											
											<td width="30%">
												${oi.product.pname}
											</td>
											
											<td width="20%">
												￥${oi.product.shop_price}
											</td>
											
											<td width="10%">
												${oi.count }
											</td>
											
											<td width="15%">
												<span class="subtotal">￥${oi.subtotal }</span>
											</td>
											
										</tr>
									</c:forEach>
									
								</tbody>
							</c:forEach>
						</table>
					</div>
				</div>
				
			<%@include file="/jsp/page.jsp" %>
		 </c:if>
		</div>
         
		<script type="text/javascript">
		  function deleteFrom(oid){
			if(confirm("您确定要删除吗?")){
			 	location.href="${pageContext.request.contextPath }/order?method=removeById&oid="+oid;
			}
		 }
	    </script>
	    
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
			Copyright &copy;米奇商城 版权所有
		</div>
	
	</body>
</html>