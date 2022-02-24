package com.itheima.web.servlet;

import java.io.IOException;



import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.web.servlet.base.BaseServlet;


/**
 * 后台分类管理模块
 */
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 添加分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//封装category对象
			Category c = new Category();
			c.setCid(UUIDUtils.getId());
			c.setCname(request.getParameter("cname"));
			//调用service完成添加操作
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			cs.save(c);
			response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
	
	/**
	 * 跳转到添加页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取请求参数
	    String style = request.getParameter("style");
	    if("notice".equals(style)){
	    	return "/admin/notices/add.jsp";
	    }else if("category".equals(style)){
	    	return "/admin/category/add.jsp";
	    }
	    return null;
	}
	/**
	 * 展示所有分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//调用service 获取所有的分类
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			List<Category> list=cs.findList();
			//将返回值放入request域中 请求转发
			request.setAttribute("list", list);
			return "/admin/category/list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	/**
	 * 后台根据分类id删除分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteCategoryById(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// 获取请求参数，分类id
		String cid = request.getParameter("cid");
		//调用service 获取所有的分类
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		try {
			cs.deleteCategoryByid(cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		return;
	}
	
	/**
	 * 后台根据分类id修改分类
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void editCategoryById(HttpServletRequest request, HttpServletResponse response)throws Exception {
		response.setContentType("text/html;charset=utf-8");
		// 获取请求参数，分类id，名称
		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		String type = request.getParameter("type");
		//调用service 获取所有的分类
		CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		try {
			if(type.equals("edit")){
				Category c = new Category();
				c.setCid(cid);
				c.setCname(cname);
				cs.editsave(c);
				response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
				return;
			}else{
				// 调用service层方法，通过分类id查找分类
				Category ct = cs.findCategoryById(cid);
				request.setAttribute("category", ct);
				request.getRequestDispatcher("/admin/category/edit.jsp").forward(request, response);
			    return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 后台根据分类名称查找分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException, IOException 
	 */
	public void findCategoryByName(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		 //获取分类名称
		String cname = request.getParameter("cname");
		try {
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			List<Category> list = cs.findCategoryByName(cname);
			request.setAttribute("list", list);
			request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
}
