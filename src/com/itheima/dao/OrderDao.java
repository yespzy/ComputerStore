package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;

public interface OrderDao {

	void save(Order order) throws Exception;

	void saveItem(OrderItem oi) throws Exception;

	int getTotalRecord(String uid) throws Exception;

	List<Order> findMyOrdersByPage(PageBean<Order> pb, String uid) throws Exception;

	Order getById(String oid) throws Exception;

	void update(Order order) throws Exception;

	List<Order> findAllByState(String state) throws Exception;

	List<OrderItem> findOrderItemByOrder(Order order)throws Exception;

	void delOrderItems(String oid)throws Exception;

	void delOrderById(String oid)throws Exception;

	List<Order> findOrder(String oid, String receiverName)throws Exception;

	Order findOrderDetailsById(String oid)throws Exception;

	void updatefinishOrderState(String oid)throws Exception;



}
