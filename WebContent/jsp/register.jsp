<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head></head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/form.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
		<style>
			  body{
			   margin-top:20px;
			   margin:0 auto;
			 }
			 .carousel-inner .item img{
				 width:100%;
				 height:300px;
			 }
			font {
			    color: #666;
			    font-size: 22px;
			    font-weight: normal;
			    padding-right:17px;
			}
			@media (min-width: 768px){
			 .col-sm-6 {
			          width:auto; 
			    }
			}
			.change-img a:hover{
			   text-decoration: none;
			   color:#990099;
			}
	   </style>
	  <script type="text/javascript">
           function changeImage() {
		         // 改变验证码图片中的文字
		        document.getElementById("img").src = "${pageContext.request.contextPath}/imageCode?time="
			    + new Date().getTime();
           }
     </script>
</head>
<body>
			
			<div class="container-fluid">
			
				<div class="col-md-4">
					<img src="${pageContext.request.contextPath}/images/client/logo.gif" style="width:170px;height:67px;margin-left:-15px;"/>
				</div>
				
				<div class="col-md-5">
					
				</div>
				
				<div class="col-md-3" style="padding-top:20px">
					<ol class="list-inline">
						<li><a href="${pageContext.request.contextPath }/user?method=loginUI">登录</a></li>
						<li><a href="#">注册</a></li>
						<li><a href="/store/jsp/cart.jsp">购物车</a></li>
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
							<a class="navbar-brand" href="${pageContext.request.contextPath }/jsp/index.jsp">首页</a>
						</div>
			
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						    <ul class="nav navbar-nav" id="c_ul">
							</ul>
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

			<div class="container" style="width:100%;background-color:#FF2C4C;">
				<div class="row"> 
				
					<div class="col-md-2"></div>
					
					<div class="col-md-8" style="background:#fff;padding:40px 80px;margin:30px;border: 1px solid #EEEDDB;">
						<font>&nbsp;&nbsp;&nbsp;注册</font>
						<form class="form-horizontal" style="margin-top:5px;" method="post" action="${pageContext.request.contextPath }/user">
							<input type="hidden" name="method" value="regist">
							
							 <div class="form-group">
							    <label for="username" class="col-sm-2 control-label">用户名</label>
							    <div class="col-sm-6">
							       <table>
						              <tr>
							            <td>
								           <input type="text" class="form-control" id="username" placeholder="请输入用户名" name="username" onkeyup="checkUsername();">
							            </td>
							            <td>
								            <span id="usernameMsg" style="margin-left:30px;"></span>
								            <span style="color:#999999">字母数字下划线,不能是数字开头</span>
							            </td>
						              </tr>
					               </table>
							    </div>
							  </div>
							  
							   <div class="form-group">
							    <label for="password" class="col-sm-2 control-label">密码</label>
							    <div class="col-sm-6">
							       <table>
						              <tr>
							            <td>
								           <input type="password" class="form-control" id="password" placeholder="请输入密码" name="password" onkeyup="checkPassword()">
							            </td>
							            <td>
								            <span id="passwordMsg" style="margin-left:30px;"></span>
								            <span style="color:#999999">密码请设置6-16位字符</span>
							            </td>
						              </tr>
					               </table>
							    </div>
							  </div>
							  
							   <div class="form-group">
							    <label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
							    <div class="col-sm-6">
							        <table>
						              <tr>
							            <td>
								          <input type="password" class="form-control" id="confirmpwd" placeholder="请输入确认密码" onkeyup="checkConfirm()">
							            </td>
							            <td>
								            <span id="confirmMsg" style="margin-left:30px;"></span>
								            <span style="color:#999999">&nbsp;</span>
							            </td>
						              </tr>
					               </table>
							    </div>
							  </div>
							  
							  <div class="form-group">
							    <label for="email" class="col-sm-2 control-label">Email</label>
							    <div class="col-sm-6">
							       <table>
						              <tr>
							            <td>
								           <input type="email" class="form-control" id="email" placeholder="Email" name="email" onkeyup="checkEmail();">
							            </td>
							            <td>
								            <span id="emailMsg" style="margin-left:30px;"></span>
								            <span style="color:#999999">请输入有效的邮箱地址</span>
							            </td>
						              </tr>
					               </table>
							    </div>
							  </div>
							  
							 <div class="form-group">
							    <label for="usercaption" class="col-sm-2 control-label">姓名</label>
							    <div class="col-sm-6">
							        <table>
						              <tr>
							            <td>
								           <input type="text" class="form-control" name="name" id="usercaption" placeholder="请输入姓名" onkeyup="checkName()">
							            </td>
							            <td>
								            <span id="nameMsg" style="margin-left:30px;"></span>
								            <span style="color:#999999">2~3位中文真实姓名</span>
							            </td>
						              </tr>
					               </table>
							    </div>
							  </div>
							  
							  <div class="form-group opt">  
							       <label for="inlineRadio1" class="col-sm-2 control-label">性别</label>  
							          <div class="col-sm-6">
							               <label class="radio-inline">
							                    <input type="radio" name="sex" id="inlineRadio1" value="1"> 男
							               </label>
							               <label class="radio-inline">
							                    <input type="radio" name="sex" id="inlineRadio2" value="0"> 女
							               </label>
							          </div>
							  </div>		
							  
							  <div class="form-group">
							    <label for="telephone" class="col-sm-2 control-label">联系号码</label>
							    <div class="col-sm-6">
							        <table>
						              <tr>
							            <td>
								           <input type="text" class="form-control" name="telephone" id="telephone" placeholder="联系号码" onkeyup="checkPhone()">
							            </td>
							            <td>
								            <span id="telephoneMsg" style="margin-left:30px;"></span>
								            <span style="color:#999999">以1开头的11位联系号码</span>
							            </td>
						              </tr>
					               </table>	      
							    </div>
							  </div>
							  
							  <div class="form-group">
							    <label for="verification-code" class="col-sm-2 control-label">验证码</label>
							    <div class="col-sm-3">
							       <input type="text" class="form-control"  >
							    </div>
							    <div class="col-sm-2 change-img" style="width:auto;">
							       <img src="${pageContext.request.contextPath}/imageCode" width="180" height="30" style="height: 30px;" id="img"/>
								   <a href="javascript:void(0);" onclick="changeImage()">看不清换一张</a>
							    </div>
							  </div>
							  
							  <div class="form-group">
							    <div class="col-sm-offset-2 col-sm-10">
							      <input type="submit"  value="" name="submit" style="background: url('${pageContext.request.contextPath}/images/client/regist_submit.gif'); color:white; width:200px; height:50px;">
							    </div>
							  </div>
							  
							</form>
					</div>
					<div class="col-md-2"></div>
				</div>
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
				Copyright &copy;米奇商城 版权所有
			</div>

</body>
</html>




