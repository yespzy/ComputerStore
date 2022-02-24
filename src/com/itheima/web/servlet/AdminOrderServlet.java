package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.constant.Constant;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.JsonUtil;
import com.itheima.web.servlet.base.BaseServlet;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * 后台订单模块
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 修改订单状态
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updateState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取订单id
			String oid = request.getParameter("oid");
			//调用service 获取订单
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			//设置订单的状态,更新
			order.setState(Constant.ORDER_YIFAHUO);
			os.update(order);
			//重定向
			response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAllByState&state=1&style=order");
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 展示订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String showDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=utf-8");
			//获取订单id
			String oid = request.getParameter("oid");
			//调用service 获取订单
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			//获取订单的订单项列表 转成json写回浏览器
			if(order != null){
				List<OrderItem> list = order.getItems();
				if(list != null && list.size()>0){				
					JsonConfig config = JsonUtil.configJson(new String[]{"order","pdate","pdesc","itemid"});
					response.getWriter().println(JSONArray.fromObject(list, config));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 后台按照状态查询订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllByState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String style = request.getParameter("style");
		if("notice".equals(style)){	
			response.sendRedirect(request.getContextPath()+"/adminNotice?method=findAllNotice");
		}else if(style.equals("order")){
			try {
				//获取订单state
				String state = request.getParameter("state");
				//调用service 获取不同的列表
				OrderService os = (OrderService) BeanFactory.getBean("OrderService");
				List<Order> list=os.findAllByState(state);
				//将list放入request域中,请求转发
				request.setAttribute("list", list);
				return "/admin/order/list.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	
	/**
	 * 后台按照多条件查询订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findorder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取订单id和收件人名称
		String oid = request.getParameter("oid");
		String receiverName = request.getParameter("receiverName");
        //创建Service层对象
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		List<Order> list;
		try {
			list = os.findorder(oid, receiverName);
			//将查询结果添加到request作用域中
			request.setAttribute("list", list);
	        //请求转发到list.jsp页面，并将request请求和response响应也转发到该页面中
			request.getRequestDispatcher("/admin/order/list.jsp").forward(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 删除已完成订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void deleteOrderById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		try {
			os.deleteOrderById(oid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAllByState&style=order");
		return;
	}
	
	/**
	 * 后台查看订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findorderDetailsById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		//获取要查询的订单id
		String oid = request.getParameter("oid");
		//根据订单id查找订单
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order;
		try {
			order = os.findorderDetailsById(oid);
			//将查询出的订单信息添加到request作用域中
			request.setAttribute("order", order);
			request.getRequestDispatcher("/admin/order/view.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
}
