package com.itheima.dao.impl;

import java.sql.ResultSet;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;


import com.itheima.dao.OrderDao;
import com.itheima.domain.Category;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.utils.DataSourceUtils;


public class OrderDaoImpl implements OrderDao {

	@Override
	/**
	 * 保存订单
	 */
	public void save(Order o) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(), sql, o.getOid(),o.getOrdertime(),o.getTotal(),
				o.getState(),o.getAddress(),o.getName(),
				o.getTelephone(),o.getUser().getUid());
	}

	@Override
	/**
	 * 保存订单项
	 */
	public void saveItem(OrderItem oi) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(), sql, oi.getItemid(),oi.getCount(),oi.getSubtotal(),
				oi.getProduct().getPid(),oi.getOrder().getOid());
	}

	@Override
	/**
	 * 获取我的订单的总条数
	 */
	public int getTotalRecord(String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from orders where uid = ?";
		return ((Long)qr.query(sql, new ScalarHandler(), uid)).intValue();
	}

	@Override
	/**
	 * 获取我的订单 当前页数据
	 */
	public List<Order> findMyOrdersByPage(PageBean<Order> pb, String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//查询所有订单(基本信息)
		String sql="select * from orders where uid = ? order by ordertime desc limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class), uid,pb.getStartIndex(),pb.getPageSize());
		//遍历订单集合 获取每一个订单,查询每个订单订单项
		for (Order order : list) {
			sql="SELECT * FROM orderitem oi,product p WHERE oi.pid = p.pid AND oi.oid = ?";
			List<Map<String, Object>> maplist = qr.query(sql, new MapListHandler(), order.getOid());
			//遍历maplist 获取每一个订单项详情,封装成orderitem,将其加入当前订单的订单项列表中
			for (Map<String, Object> map : maplist) {
				//封装成orderitem
				OrderItem oi = new OrderItem();
				BeanUtils.populate(oi, map);
				//手动封装product
				Product p = new Product();
				BeanUtils.populate(p, map);
				oi.setProduct(p);
				//将orderitem放入order的订单项列表
				order.getItems().add(oi);
			}
		}
		return list;
	}

	@Override
	/**
	 * 订单详情
	 */
	public Order getById(String oid) throws Exception {
		//查询订单基本信息
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql ="select * from orders where oid = ?";
		Order order = qr.query(sql, new BeanHandler<>(Order.class), oid);
		//查询订单项
		sql ="SELECT * FROM orderitem oi,product p WHERE oi.pid = p.pid AND oi.oid = ?";
		//所有的订单项详情
		List<Map<String, Object>> maplist = qr.query(sql, new MapListHandler(), oid);
		//遍历 获取每一个订单项详情 封装成orderitem 加入到当前订单的items中
		for (Map<String, Object> map : maplist) {
			//创建ordreitem
			OrderItem oi = new OrderItem();
			//封装
			BeanUtils.populate(oi, map);
			//手动封装product
			Product p = new Product();
			BeanUtils.populate(p, map);
			oi.setProduct(p);
			//将orderitem加入到订单的items中
			order.getItems().add(oi);
		}
		return order;
	}

	@Override
	/**
	 * 更新订单
	 */
	public void update(Order order) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update orders set state = ?,address = ?,name =?,telephone = ? where oid = ?";
		qr.update(sql,order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
		
	}

	@Override
	/**
	 * 后台查询订单列表
	 */
	public List<Order> findAllByState(String state) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders ";
		//判断state是否为空
		if(null==state || state.trim().length()==0){
			sql +=" order by ordertime desc";
			return qr.query(sql, new BeanListHandler<>(Order.class));
		}
		sql += " where state = ? order by ordertime desc";
		return qr.query(sql, new BeanListHandler<>(Order.class),state);
	}

	@Override
	public List<OrderItem> findOrderItemByOrder(final Order order) throws SQLException{
		String sql = "select * from category,orderitem,product where product.pid=orderitem.pid and category.cid=product.cid and oid=?";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		return runner.query(sql, new ResultSetHandler<List<OrderItem>>() {
			public List<OrderItem> handle(ResultSet rs) throws SQLException {
				List<OrderItem> items = new ArrayList<OrderItem>();
				while (rs.next()) {
					OrderItem item = new OrderItem();
					item.setOrder(order);
					item.setItemid(rs.getString("itemid"));
					item.setCount(rs.getInt("count"));
					item.setSubtotal(rs.getDouble("subtotal"));
					
					Product product = new Product();
					product.setPid(rs.getString("pid"));
					product.setPname(rs.getString("pname"));
					product.setMarket_price(rs.getDouble("market_price"));
					product.setShop_price(rs.getDouble("shop_price"));
					product.setPimage(rs.getString("pimage"));
					product.setPdate(rs.getDate("pdate"));
					product.setIs_hot(rs.getInt("is_hot"));
					product.setPdesc(rs.getString("pdesc"));
					product.setPflag(rs.getInt("pflag"));
	
					Category category = new Category();
					category.setCid(rs.getString("cid"));
					category.setCname(rs.getString("cname"));
					
			        product.setCategory(category);
					item.setProduct(product);
					items.add(item);
				}
				return items;
			}
		}, order.getOid());
	}

	@Override
	/**
	 * 删除订单项
	 */
	public void delOrderItems(String oid) throws Exception {
        String sql="delete from orderitem where orderitem.oid=?";
		QueryRunner runner=new QueryRunner();
		runner.update(DataSourceUtils.getConnection(),sql,oid);
	}

	@Override
	/**
	 * 删除订单
	 */
	public void delOrderById(String oid) throws Exception {
		String sql="delete from orders where oid=?";		
		QueryRunner runner = new QueryRunner();		
		runner.update(DataSourceUtils.getConnection(),sql,oid);	
	}

	@Override
	/**
	 *多条件查找订单
	 */
	public List<Order> findOrder(String oid, String receiverName) throws Exception {
		//创建集合对象
		List<Object> objs = new ArrayList<Object>();
		//定义查询sql
		String sql = "select orders.*,user.* from orders,user where user.uid=orders.uid ";
		//根据参数拼接sql语句
		if (oid != null && oid.trim().length() > 0) {
			sql += " and orders.oid=?";
			objs.add(oid);
		}
		if (receiverName != null && receiverName.trim().length() > 0) {
			sql += " and orders.name=?";
			objs.add(receiverName);
		}
		    sql += " order by orders.uid";
		//创建QueryRunner对象
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		//返回QueryRunner对象query方法的执行结果
		return runner.query(sql, new ResultSetHandler<List<Order>>() {
		public List<Order> handle(ResultSet rs) throws SQLException {
			List<Order> orders = new ArrayList<Order>();
		    //循环遍历出订单和用户信息
			while (rs.next()) {
				Order order = new Order();
				order.setOid(rs.getString("orders.oid"));
				order.setOrdertime(rs.getDate("orders.ordertime"));
				order.setTotal(rs.getDouble("orders.total"));
				order.setState(rs.getInt("orders.state"));
				order.setAddress(rs.getString("orders.address"));
				order.setName(rs.getString("orders.name"));
				order.setTelephone(rs.getString("orders.telephone"));
				orders.add(order);
				
				User user = new User();
				user.setUid(rs.getString("user.uid"));
				user.setUsername(rs.getString("user.username"));
				user.setPassword(rs.getString("user.password"));
			    user.setName(rs.getString("user.name"));
				user.setEmail(rs.getString("user.email"));
				user.setTelephone(rs.getString("user.telephone"));
				user.setSex(rs.getString("user.sex"));
				user.setState(rs.getInt("user.state"));
				user.setCode(rs.getString("user.code"));
				
				order.setUser(user);
			}
			return orders;
		}
	  }, objs.toArray());
	}

	@Override
	/**
	 *查看订单详情
	 */
	public Order findOrderDetailsById(String oid) throws Exception {
		String sql = "select * from orders,user where orders.uid=user.uid and orders.oid=?";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		return runner.query(sql, new ResultSetHandler<Order>() {
			public Order handle(ResultSet rs) throws SQLException {
				Order order = new Order();
				while (rs.next()) {
					order.setOid(rs.getString("orders.oid"));
					order.setOrdertime(rs.getDate("orders.ordertime"));
					order.setTotal(rs.getDouble("orders.total"));
					order.setState(rs.getInt("orders.state"));
					order.setAddress(rs.getString("orders.address"));
					order.setName(rs.getString("orders.name"));
					order.setTelephone(rs.getString("orders.telephone"));

					User user = new User();
					user.setUid(rs.getString("user.uid"));
					user.setUsername(rs.getString("user.username"));
					user.setPassword(rs.getString("user.password"));
				    user.setName(rs.getString("user.name"));
					user.setEmail(rs.getString("user.email"));
					user.setTelephone(rs.getString("user.telephone"));
					user.setSex(rs.getString("user.sex"));
					user.setState(rs.getInt("user.state"));
					user.setCode(rs.getString("user.code"));
					
					order.setUser(user);
				}
				return order;
			}
		}, oid);
	}

	@Override
	/**
	 *修改订单最后完成状态为3
	 */
	public void updatefinishOrderState(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update orders set state = ? where oid = ?";
		qr.update(sql,3,oid);
	}


}
