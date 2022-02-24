package com.itheima.dao.impl;

import java.util.List;




import com.itheima.dao.NoticeDao;
import com.itheima.domain.Notice;
import com.itheima.utils.DataSourceUtils;

public class NoticeDaoImpl implements NoticeDao {

	@Override
	/**
	 * 前台系统，查询最新添加或修改的一条公告
	 */
	public Notice getRecentNotice() throws Exception {
		String sql = "select * from notice order by n_time desc limit 0,1";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		return runner.query(sql, new BeanHandler<Notice>(Notice.class));
	}

	@Override
	/**
	 * 后台展示所有公告
	 */
	public List<Notice> findAll() throws Exception {
		String sql = "select * from notice order by n_time desc limit 0,10";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Notice>(Notice.class));
	}

	@Override
	/**
	 * 后台系统，根据id查找公告
	 */
	public Notice findNoticeById(String n_id) throws Exception {
		String sql = "select * from notice where n_id = ?";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		return runner.query(sql, new BeanHandler<Notice>(Notice.class),n_id);
	}

	@Override
	/**
	 * 后台系统，根据id删除公告
	 */
	public void deleteNoticeById(String n_id) throws Exception {
		String sql = "delete from notice where n_id = ?";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		runner.update(sql, n_id);
	}

	@Override
	/**
	 * 后台系统，添加公告
	 */
	public void addNotice(Notice notice) throws Exception {
		String sql = "insert into notice(title,details,n_time) values(?,?,?)";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		runner.update(sql, notice.getTitle(),notice.getDetails(),notice.getN_time());
	}

	@Override
	/**
	 * 后台系统，修改公告
	 */
	public void editNotice(Notice notice) throws Exception {
		String sql = "update notice set title=?,details=?,n_time=? where n_id=?";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		runner.update(sql, notice.getTitle(),notice.getDetails(),notice.getN_time(),notice.getN_id());
	}


}
