<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="${pageContext.request.contextPath}/css/left.css" rel="stylesheet" type="text/css"/>
<link rel="styleSheet" href="${pageContext.request.contextPath}/css/dtree.css" type="text/css" />
</head>
<body>

<table width="100" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="12"></td>
  </tr>
</table>

<table width="100%" border="0">
  <tr>
    <td>
		<div class="dtree">
			<a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/dtree.js"></script>
			<script type="text/javascript">
				d = new dTree('d');
				d.add('01',-1,'系统菜单树');
				
				d.add('0102','01','分类管理','','','mainFrame');
				d.add('010201','0102','分类列表','${pageContext.request.contextPath}/adminCategory?method=findAll&style=category','','mainFrame');
				d.add('010202','0102','添加分类','${pageContext.request.contextPath}/adminCategory?method=addUI&style=category','','mainFrame');
				
				d.add('0103','01','公告管理');
				d.add('010301','0103','公告列表','${pageContext.request.contextPath}/adminOrder?method=findAllByState&style=notice','','mainFrame');
				d.add('010302','0103','添加公告','${pageContext.request.contextPath}/adminCategory?method=addUI&style=notice','','mainFrame');
				
				d.add('0104','01','商品管理');
				d.add('010401','0104','商品列表','${pageContext.request.contextPath}/adminProduct?method=findAll','','mainFrame');
				d.add('010402','0104','添加商品','${pageContext.request.contextPath}/adminProduct?method=addUI','','mainFrame');
				
				d.add('0105','01','订单管理');
				d.add('010501','0105','订单列表','${pageContext.request.contextPath}/adminOrder?method=findAllByState&style=order','','mainFrame');
				d.add('010502','0105','未付款订单','${pageContext.request.contextPath}/adminOrder?method=findAllByState&state=0&style=order','','mainFrame');
				d.add('010503','0105','已付款订单','${pageContext.request.contextPath}/adminOrder?method=findAllByState&state=1&style=order','','mainFrame');
				d.add('010504','0105','已发货订单','${pageContext.request.contextPath}/adminOrder?method=findAllByState&state=2&style=order','','mainFrame');
				d.add('010505','0105','已完成订单','${pageContext.request.contextPath}/adminOrder?method=findAllByState&state=3&style=order','','mainFrame');

				document.write(d);
			</script>
		</div>	
    </td>
  </tr>
</table>
</body>
</html>
