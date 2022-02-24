<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		<script type="text/javascript">
			function addCategory(){
				window.location.href = "${pageContext.request.contextPath}/admin/category/add.jsp";
			}
			//删除分类
			function c_del() {   
				var msg = "您确定要删除该商品吗？";   
				if (confirm(msg)==true){   
				return true;   
				}else{   
				return false;   
				}   
			}   
		</script>
	</HEAD>
	<body>
		<br/>
		<form id="Form" name="Form" action="${pageContext.request.contextPath}/adminCategory?method=findCategoryByName" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0" style="font-size:14px;">
				<TBODY>
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" style="font-size:12pt;">
						<strong>查 询 条 件</strong>
					</td>
				</tr>
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" border="0" width="100%" bgColor="#ffffff">
							<tr>
							  <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						                      分类名称：
							  </td>
							  <td class="ta_01" bgColor="#ffffff" colspan="3">
								<input type="text" name="cname" size="15" value="" id="Form1_userName" class="bg"/>
							  </td>			
							</tr>
							<tr>
								<td width="100" height="22" align="center" class="ta_01"></td>
								<td class="ta_01">
									<font face="宋体" color="red"> &nbsp;</font>
								</td>
								<td align="right" class="ta_01">
									<br/><br/>
								</td>
								<td align="right" class="ta_01">
									<button type="submit" id="search" name="search" value="&#26597;&#35810;" class="button_view">&#26597;&#35810;</button> 
									    &nbsp;&nbsp;
									<input type="reset" name="reset" value="&#37325;&#32622;" class="button_view" />
									&nbsp;&nbsp;&nbsp; 
									<button type="button" id="add" name="add" value="添加" class="button_add" onclick="addCategory()">&#28155;&#21152;</button>
								</td>
							</tr>
						</table>
					</td>
				</tr>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>分类列表</strong>
						</TD>
					</tr>
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all" bordercolor="gray" border="1" id="DataGrid1" style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word" FONT-SIZE: 12pt;>
								<tr style="FONT-SIZE: 12pt;  HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">
									<td align="center" width="18%">
										编号
									</td>
									<td align="center" width="17%">
										分类名称
									</td>
									<td width="7%" align="center">
										编辑
									</td>
									<td width="7%" align="center">
										删除
									</td>
								</tr>
								<c:forEach var="c" items="${list}">
									<tr onmouseover="this.style.backgroundColor = 'white'"
										onmouseout="this.style.backgroundColor = '#F5FAFE';">
										<td style="CURSOR: hand; HEIGHT: 22px" align="center" width="18%">
											${c.cid }
										</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="17%">
											${c.cname }
										</td>
										<td align="center" style="HEIGHT: 22px">
											<a  href="${ pageContext.request.contextPath }/adminCategory?method=editCategoryById&cid=${c.cid}&cname=${c.cname}&type=">
												<img src="${pageContext.request.contextPath}/images/admin/order_edit.gif" border="0" style="CURSOR: hand">
											</a>
										</td>
									
										<td align="center" style="HEIGHT: 22px">
											<a href="${ pageContext.request.contextPath }/adminCategory?method=deleteCategoryById&cid=${c.cid}">
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

