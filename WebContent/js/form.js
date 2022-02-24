var emailObj;
var usernameObj;
var passwordObj;
var confirmObj;
var nameObj;
var telephoneObj;

var emailMsg;
var usernameMsg;
var passwordMsg;
var confirmMsg;
var nameMsg;
var telephoneMsg;

window.onload = function() {	// 页面加载之后, 获取页面中的对象
	emailObj = document.getElementById("email");
	usernameObj = document.getElementById("username");
	passwordObj = document.getElementById("password");
	confirmObj = document.getElementById("confirmpwd");
    nameObj = document.getElementById("usercaption");
    telephoneObj = document.getElementById("telephone");
    
	emailMsg = document.getElementById("emailMsg");
	usernameMsg = document.getElementById("usernameMsg");
	passwordMsg = document.getElementById("passwordMsg");
	confirmMsg = document.getElementById("confirmMsg");
	nameMsg = document.getElementById("nameMsg");
	telephoneMsg = document.getElementById("telephoneMsg");
};

function checkForm() {// 验证整个表单
	var bEmail = checkEmail();
	var bUsername = checkUsername();
	var bPassword = checkPassword();
	var bConfirm = checkConfirm();
	var bName = checkName();
	var bTelephone = checkPhone();
	return bEmail && bUsername && bPassword && bConfirm && bName && bTelephone;	// return false后, 事件将被取消
}

function checkEmail() {			// 验证邮箱
	var regex = /^[\w-]+@([\w-]+\.)+[a-zA-Z]{2,4}$/;
	var value =emailObj.value;
	var msg = "";
	if (!value)
		msg = "邮箱必须填写：";
	else if (!regex.test(value))
		msg = "邮箱格式不合法：";
	emailMsg.innerHTML = msg;
	emailObj.parentNode.parentNode.style.color = msg == "" ? "black" : "red";
	return msg == "";
}

function checkUsername() {		// 验证用户名
	var regex = /^[a-zA-Z_]\w{0,9}$/;	// 字母数字下划线1到10位, 不能是数字开头
	var value = usernameObj.value;// 获取usernameObj中的文本
	var msg = "";						// 最后的提示消息, 默认为空
	if (!value)							// 如果用户名没填, 填了就是一个字符串可以当作true, 没填的话不论null或者""都是false
		msg = "用户名必须填写：";			// 改变提示消息
	else if (!regex.test(value))		// 如果用户名不能匹配正则表达式规则
		msg = "用户名不合法：";			// 改变提示消息
	usernameMsg.innerHTML = msg;		// 将提示消息放入SPAN
	usernameObj.parentNode.parentNode.style.color = msg == "" ? "black" : "red";	// 根据消息结果改变tr的颜色
	return msg == "";					// 如果提示消息为空则代表没出错, 返回true
}

function checkPassword() {// 验证密码
	var regex = /^.{6,16}$/;//任意字符, 6到16位
	var value = passwordObj.value;
	var msg = "";
	if (!value)
		msg = "密码必须填写：";
	else if (!regex.test(value))
		msg = "密码不合法：";
	passwordMsg.innerHTML = msg;
	passwordObj.parentNode.parentNode.style.color = msg == "" ? "black" : "red";
	return msg == "";
}

function checkConfirm() {// 验证确认密码
	var passwordValue = passwordObj.value;
	var confirmValue = confirmObj.value;
	var msg = "";

    if(!confirmValue){
	    msg = "确认密码必须填写";
	}
	else	if (passwordValue != confirmValue){
		msg = "密码必须保持一致";
    }
	confirmMsg.innerHTML = msg;
	confirmObj.parentNode.parentNode.style.color = msg == "" ? "black" : "red";
	return msg == "";
}


function checkName(){//验证姓名
	var regex = /^[\u4e00-\u9fa5]{2,3}$/;//任意中文字符, 2到3位
	var value = nameObj.value;
	var msg = "";
	if (!value)							// 如果姓名名没填, 填了就是一个字符串可以当作true, 没填的话不论null或者""都是false
		msg = "姓名必须填写：";			// 改变提示消息
	else if (!regex.test(value))		// 如果用户名不能匹配正则表达式规则
		msg = "姓名不合法：";			// 改变提示消息
	nameMsg.innerHTML = msg;		// 将提示消息放入SPAN
	nameObj.parentNode.parentNode.style.color = msg == "" ? "black" : "red";	// 根据消息结果改变颜色
	return msg == "";					// 如果提示消息为空则代表没出错, 返回true
}


function checkPhone(){// 验证联系号码
	var regex = /^1[3-9]\d{9}$/;
	var value =telephoneObj.value;
	var msg = "";
	if (!value)
		msg = "联系号码必须填写：";
	else if (!regex.test(value))
		msg = "联系号码格式不合法：";
	telephoneMsg.innerHTML = msg;
	telephoneObj.parentNode.parentNode.style.color = msg == "" ? "black" : "red";
	return msg == "";
}