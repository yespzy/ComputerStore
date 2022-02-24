package com.itheima.dao;

import java.sql.SQLException;

import java.util.List;



import com.itheima.domain.PageBean;
import com.itheima.domain.Product;




public interface ProductDao {

	List<Product> findHot() throws Exception;

	List<Product> findNew() throws Exception;

	Product getById(String pid) throws Exception;

	List<Product> findByPage(PageBean<Product> pb, String cid) throws Exception;

	int getTotalRecord(String cid) throws Exception;

	List<Product> findAll() throws Exception;

	void save(Product p) throws Exception;

	Product getByName(String searchfield)throws Exception;

	void deleteProduct(String pid)throws Exception;

	Product findeditProductById(String pid)throws Exception;

	List<Product> findProduct(String pid, String pname, String minprice, String maxprice)throws Exception;

	void editproduct(Product p)throws Exception;

	List<Object[]> getWeekHotProduct()throws Exception;


}
