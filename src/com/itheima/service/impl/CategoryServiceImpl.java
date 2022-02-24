package com.itheima.service.impl;

import java.sql.SQLException;






import java.util.List;


import com.itheima.constant.Constant;
import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.JedisUtils;
import com.itheima.utils.JsonUtil;


public class CategoryServiceImpl implements CategoryService {

	@Override
	/**
	 * 后台展示所有分类
	 */
	public List<Category> findList() throws Exception {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		return cd.findAll();
	}

	@Override
	/**
	 * 查询所有分类
	 */
	public String findAll() throws Exception {
		//调用dao 查询所有分类
		List<Category> list=findList();
		//2.将list转换成json字符串
		if(list!=null && list.size()>0){
			return JsonUtil.list2json(list);
		}
		return null;
	}

	@Override
	/**
	 * 从redis中获取所有的分类
	 */
	public String findAllFromRedis() throws Exception {
		Jedis j =null;
		String value=null;
		try {
			//从redis获取分类信息
			try {
				//获取连接
				j = JedisUtils.getJedis();
				//获取数据 判断数据是否为空
				value = j.get(Constant.STORE_CATEGORY_LIST);
				// 若不为空,直接返回数据
				if(value!=null){
					System.out.println("缓存中有数据");
					return value;
				}
			} catch (Exception e) {
		    }
			//redis中 若无数据,则从mysql数据库中获取  别忘记将数据并放入redis中
			value = findAll();
			//将value放入redis中
			try {
				j.set(Constant.STORE_CATEGORY_LIST, value);
				System.out.println("已经将数据放入缓存中");
			} catch (Exception e) {}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			JedisUtils.closeJedis(j);
		}
		return value;
	}

	@Override
	/**
	 * 添加分类
	 */
	public void save(Category c) throws Exception {
		//调用dao 完成添加
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.save(c);
		//更新redis
		Jedis j = null;
		try {
			j=JedisUtils.getJedis();
			//清除redis中数据
			j.del(Constant.STORE_CATEGORY_LIST);
		} finally {
			JedisUtils.closeJedis(j);
		}
	}
	
	@Override
	/**
	 * 后台根据分类id删除分类
	 */
	public void deleteCategoryByid(String cid) throws Exception {
		//调用dao 完成删除
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		try {
			cd.deletecategoryById(cid);
		} catch (SQLException e) {
			throw new RuntimeException("后台系统根据分类id删除分类失败！");
		}
	}

	@Override
	/**
	 * 后台根据cid修改分类
	 */
	public Category findCategoryById(String cid) throws Exception {
		//调用dao 完成分类查找
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		try {
			return cd.findCategoryById(cid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("根据分类id查找分类失败");
		}
	}

	@Override
	public void editsave(Category c) throws Exception {
		//调用dao 完成修改
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		try {
			cd.editCategory(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * 添加分类
	 */
	public List<Category> findCategoryByName(String cname) throws Exception {
		//调用dao 完成添加
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		try {
			return cd.findCategoryByName(cname);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("添加分类失败！");
		}
	}

	@Override
	/**
	 * 查找分类id
	 */
	public String findcid(Category c, String cname) throws Exception {
		//调用dao 完成查找
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		try {
			return cd.findcid(c,cname);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查找分类id失败");
		}
	}
}
