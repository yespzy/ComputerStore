<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<HTML>
<HEAD>
	<meta http-equiv="Content-Language" content="zh-cn">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
	<script type="text/javascript">
	    //添加公告
		function addNotice() { 
			window.location.href = "${pageContext.request.contextPath}/admin/notices/add.jsp";
		}
		//删除公告
		function g_del() {   
			var msg = "您确定要删除该订单吗？";   
			if (confirm(msg)==true){   
			return true;   
			}else{   
			return false;   
			}  
		}
	</script>
</HEAD>
<body>
	<br />
	<form id="Form" name="Form" action="" method="post">
		<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
			<tbody>
			
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3">
					    <strong>公 告 列 表</strong>
					</td>
				</tr>
				
				<tr>
					<td class="ta_01" align="right">
						<button type="button" id="add" name="add" value="&#28155;&#21152;" class="button_add" onclick="addNotice()">
							&#28155;&#21152;
						</button>
					</td>
				</tr>
				
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						<table cellspacing="0" cellpadding="1" rules="all" bordercolor="gray" border="1" id="DataGrid1" style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
						
							<tr
								style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">
								<td align="center" width="12%">公告编号</td>
								<td align="center" width="12%">公告标题</td>
								<td align="center" width="24%">公告内容</td>
								<td align="center" width="8%">时间</td>
								<td width="8%" align="center">编辑</td>
								<td width="8%" align="center">删除</td>
							</tr>
							
							<c:forEach items="${notices}" var="n">
								<tr onmouseover="this.style.backgroundColor = 'white'" onmouseout="this.style.backgroundColor = '#F5FAFE';">
									
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="23">${n.n_id }
									</td>
									
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="18%">${n.title }
									</td>
									
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="8%">${n.details }
									</td>
									
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="8%">${n.n_time }
									</td>
									
									<td align="center" style="HEIGHT: 22px" width="7%">
										<a href="${pageContext.request.contextPath}/adminNotice?method=EditNoticeById&id=${n.n_id}">
											<img src="${pageContext.request.contextPath}/images/admin/order_edit.gif" border="0" style="CURSOR: hand"> 
										</a>
									</td>
									
									<td align="center" style="HEIGHT: 22px" width="7%">
										<a href="${pageContext.request.contextPath}/adminNotice?method=DeleteNoticeById&id=${n.n_id}" onclick="javascript:return g_del()">
											<img src="${pageContext.request.contextPath}/images/admin/order_delete.gif" width="16" height="16" border="0" style="CURSOR: hand">
										</a>
									</td>
									
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</TBODY>
		</table>
	</form>
</body>
</HTML>