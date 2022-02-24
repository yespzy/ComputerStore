package com.itheima.web.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
/**
 * 前台页面，用于菜单栏下面搜索功能的servlet
 */
public class MenuSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		this.doPost(req, resp);
	}
   public void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		// 定义当前页码，默认为1
		int currentPage = 1;
		String _currentPage = req.getParameter("currentPage");
		if (_currentPage != null) {
			currentPage = Integer.parseInt(_currentPage);
		}
		// 定义每页显示条数,默认为4
		int currentCount = 1;	
		
		//获取前台页面搜索框输入的值
		String searchfield = req.getParameter("textfield");
		//如果搜索框中没有输入值，则表单传递的为默认值，此时默认查询全部商品目录
		if("请输入商品名称".equals(searchfield) || "".equals(searchfield)){
			req.setAttribute("msg", "请输入商品名称");
			req.getRequestDispatcher("/jsp/msg.jsp").forward(req, resp);
			return;
		}
		//调用service获取单个商品 参数:pname 返回值:product
		ProductService ps =new ProductServiceImpl();
		Product bean = null;
		try {
			bean = ps.getByName(searchfield);
		} catch (Exception e) {
			e.printStackTrace();
		}
			// 将数据存储到request范围，跳转到product_search_list.jsp页面展示
			req.setAttribute("bean", bean);
		    req.getRequestDispatcher("/jsp/product_info.jsp").forward(req, resp);
	}
}
