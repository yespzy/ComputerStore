package com.itheima.service.impl;

import com.itheima.constant.Constant;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.MailUtils;

public class UserServiceImpl implements UserService {

	@Override
	/**
	 * 用户注册
	 */
	public void regist(User user) throws Exception {
		//调用dao完成注册
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		ud.save(user);
		//发送激活邮件
		String emailMsg="恭喜"+user.getUsername()+":成为我们商城的一员,<a href='http://localhost:8080/store/user?method=active&code="+user.getCode()+"'>点此激活</a>";
		MailUtils.sendMail(user.getEmail(), emailMsg);
	}

	@Override
	/**
	 * 用户激活
	 */
	public User active(String code) throws Exception {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		//通过激活码code获取用户
		User user=ud.getByCode(code);
		if(user == null){
			return null;
		}
		user.setState(Constant.USER_IS_ACTIVE);
		user.setCode(null);
		ud.update(user);
		return user;
	}

	@Override
	/**
	 * 用户登录
	 */
	public User login(String username, String password) throws Exception {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		return ud.getByUsernameAndPwd(username,password);
	}

	@Override
	/**
	 * 修改密码
	 */
	public void editpassword(String username, String newpassword, String email) throws Exception {
		//调用dao完成修改密码
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		ud.editpassword(username,newpassword,email);
	}

}
