package com.itheima.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.web.servlet.base.BaseServlet;

/**
 * 前台分类模块
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 查询所有分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=utf-8");
			//调用service,查询所有分类,返回值 json字符串
			CategoryService cs = new CategoryServiceImpl();
			//从redis中获取列表
			String value = cs.findAllFromRedis();
			//将字符串写回浏览器
			response.getWriter().println(value);
		} catch (Exception e) {
		}
		return null;
	}
}
