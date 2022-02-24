package com.itheima.service.impl;

import java.sql.SQLException;



import java.util.List;

import com.itheima.dao.CategoryDao;
import com.itheima.dao.ProductDao;
import com.itheima.domain.Category;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;



public class ProductServiceImpl implements ProductService {

	@Override
	/**
	 * 查询热门商品
	 */
	public List<Product> findHot() throws Exception {
		ProductDao pd= (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findHot();
	}

	@Override
	/**
	 * 查询最新商品
	 */
	public List<Product> findNew() throws Exception {
		ProductDao pd= (ProductDao) BeanFactory.getBean("ProductDao");
		return pd.findNew();
	}

	@Override
	/**
	 * 单个商品详情
	 */
	public Product getById(String pid) throws Exception {
		ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
		return pd.getById(pid);
	}

	@Override
	/**
	 * 分页展示分类商品
	 */
	public PageBean<Product> findByPage(int pageNumber, int pageSize, String cid) throws Exception {
		ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");
		//创建pagebean
		PageBean<Product> pb = new PageBean<>(pageNumber, pageSize);
		//设置当前页数据
		List<Product> data = pDao.findByPage(pb,cid);
		pb.setData(data);
		//设置总记录数
		int totalRecord = pDao.getTotalRecord(cid);
		pb.setTotalRecord(totalRecord);
		return pb;
	}

	@Override
	/**
	 * 后台展示已上架商品
	 */
	public List<Product> findAll() throws Exception {
		ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");
		List<Product> product = pDao.findAll();
		return product;
	}

	@Override
	/**
	 * 保存商品
	 */
	public void save(Product p) throws Exception {
		ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");
		pDao.save(p);
	}

	@Override
	/**
	 * 前台查找商品
	 */
	public Product getByName(String searchfield) throws Exception {
		ProductDao pname=(ProductDao) BeanFactory.getBean("ProductDao");
		return pname.getByName(searchfield);
	}

	@Override
	/**
	 * 后台删除商品
	 */
	public void deleteProductById(String pid) throws Exception {
		ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");
		try {
			pDao.deleteProduct(pid);
		} catch (SQLException e) {
			throw new RuntimeException("后台系统根据商品id删除商品失败！");
		}
	}

	@Override
	/**
	 *查找修改商品
	 */
	public Product editfindProductById(String pid) throws Exception {
		Product product = null;
		ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		try {
			product = pDao.findeditProductById(pid);
			List<Category> items = cd.findEditCategoryItems(product);
			product.setCategoryItems(items);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("根据商品id查找修改商品失败");
		}
		return product;
	}

	@Override
	/**
	 *多条件查找商品
	 */
	public List<Product> findproduct(String pid, String pname, String minprice, String maxprice)throws Exception {
		List<Product> ps = null;
		ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");
		try {
			ps = pDao.findProduct(pid, pname,minprice,maxprice);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}

	@Override
	/**
	 *修改商品
	 */
	public void editproduct(Product p) throws Exception {
		ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");
		try {
			pDao.editproduct(p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 *前台，获取本周热销商品
	 */
	public List<Object[]> getWeekHotProduct() throws Exception {
		ProductDao pDao= (ProductDao) BeanFactory.getBean("ProductDao");
		try {
			return pDao.getWeekHotProduct();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("前台获取本周热销商品失败！");
		}
	}


}
