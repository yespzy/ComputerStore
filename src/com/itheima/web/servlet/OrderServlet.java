package com.itheima.web.servlet;

import java.io.IOException;



import java.util.Date;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.itheima.constant.Constant;
import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.web.servlet.base.BaseServlet;

/**
 * 订单模块
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 获取订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取订单id
			String oid = request.getParameter("oid");
			//调用service 查询单个订单 
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			//请求转发
			request.setAttribute("bean",order);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查询订单详情失败");
			return "/jsp/msg.jsp";
		}
		return "/jsp/order_info.jsp";
	}
	
	
	/**
	 * 删除订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String removeById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//获取订单id
			String oid = request.getParameter("oid");
			//调用service 查询单个订单 
			OrderService remove = (OrderService) BeanFactory.getBean("OrderService");
			try{
				remove.removeById(oid);
				request.setAttribute("msg", "删除订单成功！");
				return "/jsp/msg.jsp";
			}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "删除订单失败！");
			return "/jsp/msg.jsp";
		}
	}
	
	
	/**
	 * 我的订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findMyOrdersByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//获取pageNumber 设置pagesize 获取userid
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			int pageSize=3;
			User user=(User)request.getSession().getAttribute("user");
			if(user == null){
				request.setAttribute("msg", "请先登录");
				return "/jsp/msg.jsp";
			}
			//调用service获取当前页所有数据  pagebean
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			PageBean<Order> bean = os.findMyOrdersByPage(pageNumber,pageSize,user.getUid());
			//将pagebean放入request域中,请求转发 order_list.jsp
			request.setAttribute("pb", bean);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取我的订单失败");
			return "/jsp/msg.jsp";
		}
		return "/jsp/order_list.jsp";
	}
	
	/**
	 * 保存订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//从session中获取user
			User user=(User) request.getSession().getAttribute("user");
			if(user == null){
				request.setAttribute("msg", "请先登录!");
				return "/jsp/msg.jsp";
			}
			//获取购物车
			Cart cart=(Cart) request.getSession().getAttribute("cart");
			//封装订单对象
			Order order = new Order();
			order.setOid(UUIDUtils.getId());
			order.setOrdertime(new Date());
			order.setTotal(cart.getTotal());
			order.setState(Constant.ORDER_WEIFUKUAN);
			order.setUser(user);
			//设置items(订单项列表) 遍历购物项列表
			for (CartItem	ci : cart.getCartItems()) {
				//封装成orderitem
				OrderItem oi = new OrderItem();
				oi.setItemid(UUIDUtils.getId());
				oi.setCount(ci.getCount());
				oi.setSubtotal(ci.getSubtotal());
				oi.setProduct(ci.getProduct());
				oi.setOrder(order);
				//将orderitem加入order 的items中
				order.getItems().add(oi);
			}
			//调用orderservice完成保存操作
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			os.save(order);
			//清空购物车
			cart.clearCart();
			//请求转发到 order_info.jsp
			request.setAttribute("bean", order);
		} catch (Exception e) {
		}
		return "/jsp/order_info.jsp";
	}

	/**
	 * 在线支付
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String Pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//接受参数
			String address=request.getParameter("address");
			String name=request.getParameter("name");
			String telephone=request.getParameter("telephone");
			String oid=request.getParameter("oid");
			//通过订单id获取order
			OrderService s=(OrderService) BeanFactory.getBean("OrderService");
			Order order = s.getById(oid);
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			order.setState(Constant.ORDER_YIFUKUAN);
			//更新order
			s.update(order);
			request.setAttribute("msg", "支付成功！");
			return "/jsp/msg.jsp";
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "支付失败");
			return "/jsp/msg.jsp";
		}
	}
	
	/**
	 * 修改最后完成状态为3
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updatefinishOrderStateById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//通过订单id获取order
		String oid = request.getParameter("oid");
		OrderService s=(OrderService) BeanFactory.getBean("OrderService");
		try {
			s.updatefinishOrderStateById(oid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/order_list.jsp";
	}
	
}
