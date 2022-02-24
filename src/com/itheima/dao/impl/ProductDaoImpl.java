package com.itheima.dao.impl;

import java.sql.ResultSet;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




import com.itheima.constant.Constant;
import com.itheima.dao.ProductDao;
import com.itheima.domain.Category;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.utils.DataSourceUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	/**
	 * 查询热门
	 */
	public List<Product> findHot() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = ? and pflag = ? order by pdate desc limit 9";
		return qr.query(sql, new BeanListHandler<>(Product.class), Constant.PRODUCT_IS_HOT,Constant.PRODUCT_IS_UP);
	}

	@Override
	/**
	 * 查询最新
	 */
	public List<Product> findNew() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pflag = ? order by pdate desc limit 9";
		return qr.query(sql, new BeanListHandler<>(Product.class),Constant.PRODUCT_IS_UP);
	}

	@Override
	/**
	 * 查询单个商品
	 */
	public Product getById(String pid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid = ? limit 1";
		return qr.query(sql, new BeanHandler<>(Product.class), pid);
		
	}

	@Override
	/**
	 * 查询当前页数据
	 */
	public List<Product> findByPage(PageBean<Product> pb, String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid = ? and pflag = ? order by pdate desc limit ?,?";
		return qr.query(sql, new BeanListHandler<>(Product.class), cid,Constant.PRODUCT_IS_UP,pb.getStartIndex(),pb.getPageSize());
	}

	@Override
	/**
	 * 获取总记录数
	 */
	public int getTotalRecord(String cid) throws Exception {
		return ((Long)new QueryRunner(DataSourceUtils.getDataSource()).query("select count(*) from product where cid = ? and pflag = ?", new ScalarHandler(), cid,Constant.PRODUCT_IS_UP)).intValue();
	}

	@Override
	/**
	 * 后台展示上架商品
	 */
	public List<Product> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pflag = ? order by pdate desc";
		return qr.query(sql, new BeanListHandler<>(Product.class), Constant.PRODUCT_IS_UP);
	}

	@Override
	/**
	 * 保存商品
	 */
	public void save(Product p) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into product values(?,?,?,?,?,?,?,?,?,?);";
		qr.update(sql, p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(),p.getPimage(),p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getPflag(),p.getCategory().getCid());
	}

	@Override
	/**
	 * 前台查找商品
	 */
	public Product getByName(String searchfield) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pname = ?";
		return qr.query(sql, new BeanHandler<>(Product.class),searchfield);	
	}

	@Override
	/**
	 * 删除商品
	 */
	public void deleteProduct(String pid) throws Exception {
		String sql = "delete from product where pid = ?";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		runner.update(sql, pid);
	}

	@Override
	/**
	 * 查找修改商品
	 */
	public Product findeditProductById(String pid) throws Exception {
		String sql = "select * from product,category where product.cid=category.cid and product.pid=?";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		return runner.query(sql, new ResultSetHandler<Product>() {
			public Product handle(ResultSet rs) throws SQLException {
				Product product = new Product();
				while (rs.next()) {
					product.setPid(rs.getString("product.pid"));
					product.setPname(rs.getString("product.pname"));
					product.setMarket_price(rs.getDouble("product.market_price"));
					product.setShop_price(rs.getDouble("product.shop_price"));
					product.setPimage(rs.getString("product.pimage"));
					product.setPdate(rs.getDate("product.pdate"));
					product.setIs_hot(rs.getInt("product.is_hot"));
					product.setPdesc(rs.getString("product.pdesc"));
					product.setPflag(rs.getInt("product.pflag"));

					Category category = new Category();
					category.setCid(rs.getString("category.cid"));
					category.setCname(rs.getString("category.cname"));
					product.setCategory(category);
				}
				return product;
			}
		}, pid);
	}

	@Override
	/**
	 * 后台多条件查找商品
	 */
	public List<Product> findProduct(String pid, String pname, String minprice, String maxprice)throws Exception {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from product where 1=1 ";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		if (pid != null && pid.trim().length() > 0) {
			sql += " and pid=?";
			list.add(pid);
		}
		if (pname != null && pname.trim().length() > 0) {
			sql += " and pname=?";
			list.add(pname);
		}
		if (minprice != null && maxprice != null && minprice.trim().length() > 0 && maxprice.trim().length() > 0) {
			sql += " and shop_price between ? and ?";
			list.add(minprice);
			list.add(maxprice);
		}
		Object[] params = list.toArray();
		return runner.query(sql, new BeanListHandler<Product>(Product.class),params);
	}

	@Override
	/**
	 * 后台修改商品
	 */
	public void editproduct(Product p) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update product set pname = ?,market_price = ?,shop_price = ?,pimage = ?,is_hot = ?,pdesc = ?,pflag = ?,cid = ? where pid = ?";
		qr.update(sql, p.getPname(),p.getMarket_price(),p.getShop_price(),p.getPimage(),p.getIs_hot(),p.getPdesc(),p.getPflag(),p.getCategory().getCid(),p.getPid());
	}

	@Override
	/**
	 *前台，获取本周热销商品
	 */
	public List<Object[]> getWeekHotProduct() throws Exception {
		String sql = "select product.pid,product.pname, "+
	" product.pimage,SUM(orderitem.count) totalsalnum "+
        " from orderitem,orders,product "+
        " where orderitem.oid = orders.oid "+
                " and product.pid = orderitem.pid "+
                " and orders.state=6"+
        " group by product.pid,product.pname,product.pimage"+
        " order by totalsalnum desc limit 0,6";
QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
return runner.query(sql, new ArrayListHandler());
	}

}
