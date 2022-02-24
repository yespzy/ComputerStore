package com.itheima.service;

import java.util.List;


import com.itheima.domain.PageBean;
import com.itheima.domain.Product;



public interface ProductService {
	
	List<Product> findHot() throws Exception;

	List<Product> findNew() throws Exception;

	Product getById(String pid) throws Exception;

	PageBean<Product> findByPage(int pageNumber, int pageSize, String cid) throws Exception;

	List<Product> findAll() throws Exception;

	void save(Product p)throws Exception;
	
	Product getByName(String searchfield)throws Exception;

	void deleteProductById(String pid)throws Exception;

	Product editfindProductById(String pid)throws Exception;

	List<Product> findproduct(String pid, String pname, String minprice, String maxprice)throws Exception;

	void editproduct(Product p)throws Exception;

	List<Object[]> getWeekHotProduct()throws Exception;



}
