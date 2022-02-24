package com.itheima.service;

import java.util.List;

import com.itheima.domain.Order;
import com.itheima.domain.PageBean;

public interface OrderService {

	void save(Order order)throws Exception;

	PageBean<Order> findMyOrdersByPage(int pageNumber, int pageSize, String uid)throws Exception;

	Order getById(String oid)throws Exception;

	void update(Order order)throws Exception;

	List<Order> findAllByState(String state)throws Exception;

	void removeById(String oid)throws Exception;

	List<Order> findorder(String oid, String receiverName)throws Exception;

	void deleteOrderById(String oid)throws Exception;

	Order findorderDetailsById(String oid)throws Exception;

	void updatefinishOrderStateById(String oid)throws Exception;


}
