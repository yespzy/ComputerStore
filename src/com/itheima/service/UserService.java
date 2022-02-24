package com.itheima.service;

import com.itheima.domain.User;

public interface UserService {

	void regist(User user) throws Exception;

	User active(String code) throws Exception;

	User login(String username, String password) throws Exception;

	void editpassword(String username, String newpassword, String email)throws Exception;

}
