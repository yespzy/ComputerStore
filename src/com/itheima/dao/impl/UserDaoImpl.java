package com.itheima.dao.impl;

import java.sql.SQLException;





import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao{

	@Override
	/**
	 * 用户注册
	 */
	public void save(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user(uid,username,password,name,email,telephone,sex,state,code) values(?,?,?,?,?,?,?,?,?)";
		qr.update(sql, user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getSex(),user.getState(),user.getCode());
	}

	@Override
	/**
	 * 通过激活码获取用户
	 */
	public User getByCode(String code) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where code = ? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class), code);
	}

	@Override
	/**
	 * 更新用户
	 */
	public void update(User user) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update user set password = ?,name = ?,sex = ?,state = ?,code = ? where uid = ?";
		qr.update(sql, user.getPassword(),user.getName(),user.getSex(),user.getState(),user.getCode(),user.getUid());
	}

	@Override
	/**
	 * 用户登录
	 */
	public User getByUsernameAndPwd(String username, String password) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ? limit 1";
		return qr.query(sql, new BeanHandler<>(User.class), username,password);
	}

	@Override
	/**
	 * 修改密码
	 */
	public void editpassword(String username, String newpassword, String email) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update user set password = ? where username = ? and email = ?";
		qr.update(sql,newpassword,username,email);
	}

}
