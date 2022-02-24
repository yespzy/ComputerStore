package com.itheima.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Notice;
import com.itheima.service.NoticeService;
import com.itheima.utils.BeanFactory;


/**
 * 后台公告管理模块
 */
public class AdminNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		//获取method值
	    String methodName = request.getParameter("method");;
		//判断method调用相应的方法
		if("findAllNotice".equals(methodName)){
			findAllNotice(request,response);
		}else if("EditNoticeById".equals(methodName)){
			EditNoticeById(request,response);
		}else if("DeleteNoticeById".equals(methodName)){
			DeleteNoticeById(request,response);
		}else if("AddNotice".equals(methodName)){
			AddNotice(request,response);
		}else if("EditNotice".equals(methodName)){
			EditNotice(request,response);
		}
	}
	
	

	public void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		this.doGet(req, resp);
	}
	

	/**
	 * 后台展示所有公告
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findAllNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			//调用service 获取所有的分类
			NoticeService ns = (NoticeService) BeanFactory.getBean("NoticeService");
			List<Notice> notices=ns.findList();
			//将返回值放入request域中 请求转发
			request.setAttribute("notices", notices);
			request.getRequestDispatcher("/admin/notices/list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 后台系统，根据id查找公告
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private void EditNoticeById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		NoticeService ns = (NoticeService) BeanFactory.getBean("NoticeService");
		//获取公告id
		String n_id = request.getParameter("id");
		Notice notice;
		try {
			notice = ns.EditNoticeById(n_id);
			request.setAttribute("n", notice);
			request.getRequestDispatcher("/admin/notices/edit.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 后台系统，根据id删除公告
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private void DeleteNoticeById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		NoticeService ns = (NoticeService) BeanFactory.getBean("NoticeService");
		//获取请求参数，公告id
		String n_id = request.getParameter("id");
		//调用dao层方法
		try {
			ns.DeleteNoticeById(n_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath()+"/adminNotice?method=findAllNotice");
	} 
	
	/**
	 * 后台系统，添加公告
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private void AddNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		NoticeService ns = (NoticeService) BeanFactory.getBean("NoticeService");
		Notice notice = new Notice();
		//获取表单参数
		String title = request.getParameter("title");
		String details = request.getParameter("details");
		
		//将当前时间设为添加公告的时间
		String t = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		notice.setTitle(title);
		notice.setDetails(details);
		notice.setN_time(t);
		//调用addNotice方法
		try {
			ns.AddNotice(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect(request.getContextPath()+"/adminNotice?method=findAllNotice");
	} 
	
	/**
	 * 后台系统，修改公告
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private void EditNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		NoticeService ns = (NoticeService) BeanFactory.getBean("NoticeService");
		Notice notice = new Notice();
		//获取表单参数
		String n_id = request.getParameter("id");
		String title = request.getParameter("title");
		String details = request.getParameter("details");
		
		//将当前时间设为添加公告的时间
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		notice.setN_id(n_id);
		notice.setTitle(title);
		notice.setDetails(details);
		notice.setN_time(time);
		
		//调用dao层方法
		try {
			ns.EditNotice(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath()+"/adminNotice?method=findAllNotice");
	}
}
