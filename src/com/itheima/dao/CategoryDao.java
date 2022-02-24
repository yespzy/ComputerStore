package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Category;
import com.itheima.domain.Product;

public interface CategoryDao {

	List<Category> findAll() throws Exception;

	void save(Category c) throws Exception;

	void deletecategoryById(String cid)throws Exception;

	Category findCategoryById(String cid)throws Exception;

	void editCategory(Category c)throws Exception;

	List<Category> findCategoryByName(String cname)throws Exception;

	List<Category> findEditCategoryItems(Product product)throws Exception;

	String findcid(Category c, String cname)throws Exception;

}
