package com.itheima.service;

import java.util.List;

import com.itheima.domain.Category;

public interface CategoryService {

	String findAll() throws Exception;

	String findAllFromRedis() throws Exception;

	List<Category> findList() throws Exception;

	void save(Category c) throws Exception;

	void deleteCategoryByid(String cid)throws Exception;

	Category findCategoryById(String cid)throws Exception;

	void editsave(Category c)throws Exception;

	List<Category> findCategoryByName(String cname)throws Exception;

	String findcid(Category c, String cname)throws Exception;


}
