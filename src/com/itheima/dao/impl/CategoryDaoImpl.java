package com.itheima.dao.impl;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.domain.Product;
import com.itheima.utils.DataSourceUtils;


public class CategoryDaoImpl implements CategoryDao {

	@Override
	/**
	 * 查询所有分类
	 */
	public List<Category> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		return qr.query(sql, new BeanListHandler<>(Category.class));
	}

	@Override
	/**
	 * 添加分类
	 */
	public void save(Category c) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into category values (?,?);";
		qr.update(sql, c.getCid(),c.getCname());
	}

	
	@Override
	/**
	 * 删除分类
	 */
	public void deletecategoryById(String cid) throws Exception {
		String sql = "delete from category where cid = ?";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		runner.update(sql, cid);
	}
	
	@Override
	/**
	 * 修改分类
	 */
	public Category findCategoryById(String cid) throws SQLException {
		String sql = "select * from  category where cid = ? limit 1";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		return qr.query(sql, new BeanHandler<>(Category.class), cid);
	}
	

	@Override
	public void editCategory(Category c) throws Exception {
		//创建集合并将商品信息添加到集合中
		List<Object> obj = new ArrayList<Object>();
		obj.add(c.getCname());
		obj.add(c.getCid());
		//创建sql语句，并拼接sql
		String sql  = "update category set category.cname = ? where category.cid=?";		
		System.out.println(sql);		
		System.out.println(obj);
		//创建QueryRunner对象
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		//使用QueryRunner对象的update()方法更新数据
		runner.update(sql, obj.toArray());

	}

	@Override
	/**
	 * 查找分类
	 */
	public List<Category> findCategoryByName(String cname) throws Exception {
		String sql = "select * from  category where category.cname = ?";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<>(Category.class),cname);
	}

	@Override
	/**
	 * 查找修改分类列表数目
	 */
	public List<Category> findEditCategoryItems(Product product) throws Exception {
		String sql = "select * from category";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		return runner.query(sql, new ResultSetHandler<List<Category>>() {
			public List<Category> handle(ResultSet rs) throws SQLException {
				List<Category> items = new ArrayList<Category>();
				while (rs.next()) {
					Category categoryitem = new Category();
					categoryitem.setCid(rs.getString("category.cid"));
					categoryitem.setCname(rs.getString("category.cname"));
					items.add(categoryitem);
				}
				return items;
			}
		});
	}

	@Override
	/**
	 * 查找分类id
	 */
	public String findcid(Category c, String cname) throws Exception {
		String sql = "select * from  category where category.cname = ?";
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		qr.query(sql, new BeanListHandler<>(Category.class),cname);
		return c.getCid();
	}


}
