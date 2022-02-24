package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;


import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.DataSourceUtils;


public class OrderServiceImpl implements OrderService {

	@Override
	/**
	 * 保存订单
	 */
	public void save(Order order) throws Exception{
		try {
			//获取dao
			OrderDao od  = (OrderDao) BeanFactory.getBean("OrderDao");
			//开启事务
			DataSourceUtils.startTransaction();
			//向orders表中插入一条数据
			od.save(order);
			//向orderitem中插入n条数据
			for (OrderItem oi : order.getItems()) {
				od.saveItem(oi);
			}
			//事务控制
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}

	@Override
	/**
	 * 我的订单
	 */
	public PageBean<Order> findMyOrdersByPage(int pageNumber, int pageSize, String uid) throws Exception {
		OrderDao od  = (OrderDao) BeanFactory.getBean("OrderDao");
		//创建pagebean
		PageBean<Order> pb = new PageBean<>(pageNumber, pageSize);
		//查询总条数  设置总条数 
		int totalRecord = od.getTotalRecord(uid);
		pb.setTotalRecord(totalRecord);
		//查询当前页数据 设置当前页数据
		List<Order> data = od.findMyOrdersByPage(pb,uid);
		pb.setData(data);
		return pb;
	}

	@Override
	/**
	 * 订单详情
	 */
	public Order getById(String oid) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.getById(oid);
	}

	@Override
	/**
	 * 修改订单
	 */
	public void update(Order order) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		od.update(order);
	}


	@Override
	/**
	 * 前台删除个人订单
	 */
	public void removeById(String oid) throws Exception {
		try {
			//获取dao
			OrderDao od  = (OrderDao) BeanFactory.getBean("OrderDao");
			DataSourceUtils.startTransaction();
			od.delOrderItems(oid);
			od.delOrderById(oid);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DataSourceUtils.rollbackAndClose();;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				DataSourceUtils.commitAndClose();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}

	
	@Override
	/**
	 * 后台查询订单列表
	 */
	public List<Order> findAllByState(String state) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.findAllByState(state);
	}

	
	@Override
	/**
	 * 后台多条件查找订单
	 */
	public List<Order> findorder(String oid, String receiverName) throws Exception {
		OrderDao od  = (OrderDao) BeanFactory.getBean("OrderDao");
		List<Order> orders = null;
		try {
			orders = od.findOrder(oid, receiverName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	@Override
	/**
	 *后台删除已完成订单
	 */
	public void deleteOrderById(String oid) throws Exception {
		try {
			//获取dao
			OrderDao od  = (OrderDao) BeanFactory.getBean("OrderDao");
			DataSourceUtils.startTransaction();
			od.delOrderItems(oid);
			od.delOrderById(oid);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DataSourceUtils.rollbackAndClose();;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				DataSourceUtils.commitAndClose();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}

	@Override
	/**
	 *后台查看订单详情
	 */
	public Order findorderDetailsById(String oid) throws Exception {
		//获取dao
		OrderDao od  = (OrderDao) BeanFactory.getBean("OrderDao");
		Order order = null;
		try {
			order = od.findOrderDetailsById(oid);
			List<OrderItem> items = od.findOrderItemByOrder(order);
			order.setItems(items);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	/**
	 *将订单状态修改成已完成3
	 */
	public void updatefinishOrderStateById(String oid) throws Exception {
		//获取dao
		OrderDao od  = (OrderDao) BeanFactory.getBean("OrderDao");
		od.updatefinishOrderState(oid);
	}
}
