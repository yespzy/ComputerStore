package com.itheima.web.servlet;

import java.io.IOException;


import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Notice;
import com.itheima.domain.Product;
import com.itheima.service.NoticeService;
import com.itheima.service.ProductService;
import com.itheima.service.impl.NoticeServiceImpl;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.web.servlet.base.BaseServlet;


/**
	首页模块
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	@Override
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//调用productservice查询最新商品 和 热门商品
			ProductService ps = new ProductServiceImpl();
			List<Product> hotList=ps.findHot();
			List<Product> newList=ps.findNew();
			request.setAttribute("hList", hotList);
			request.setAttribute("nList", newList);
			
			//查询最近一条公告，传递到index.jsp页面进行展示
			NoticeService noticeService = new NoticeServiceImpl();
			Notice notice = noticeService.getRecentNotice();
			request.setAttribute("n", notice);
			
			//查询本周热销的两条商品，传递到index.jsp页面进行展示
			List<Object[]> pList =  ps.getWeekHotProduct();
			request.setAttribute("pList", pList);
	
		} catch (Exception e) {
		}
		
		return "/jsp/index.jsp";
	}
}
