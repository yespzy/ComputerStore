package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.CategoryDao;
import com.itheima.dao.NoticeDao;
import com.itheima.domain.Notice;
import com.itheima.service.NoticeService;
import com.itheima.utils.BeanFactory;

public class NoticeServiceImpl implements NoticeService {
	private NoticeDao dao = (NoticeDao) BeanFactory.getBean("NoticeDao");

	@Override
	/**
	 * 前台系统，查询最新添加或修改的一条公告
	 */
	public Notice getRecentNotice() throws Exception {
		try {
			return dao.getRecentNotice();
		} catch (SQLException e) {
			throw new RuntimeException("查询最新添加或修改的一条公告失败!");
		}
	}

	@Override
	/**
	 * 后台展示所有公告
	 */
	public List<Notice> findList() throws Exception {
		try {
			return dao.findAll();
		} catch (Exception e) {
			throw new RuntimeException("查询所有的公告失败！");
		}
	}

	@Override
	/**
	 * 后台系统，根据id查找公告
	 */
	public Notice EditNoticeById(String n_id) throws Exception {
		try {
			return dao.findNoticeById(n_id);
		} catch (SQLException e) {
			throw new RuntimeException("根据id查找公告失败！");
		}
	}

	@Override
	/**
	 * 后台系统，根据id删除公告
	 */
	public void DeleteNoticeById(String n_id) throws Exception {
		try {
			dao.deleteNoticeById(n_id);
		} catch (SQLException e) {
			throw new RuntimeException("根据id删除公告失败！");
		}
	}

	@Override
	/**
	 * 后台系统，添加公告
	 */
	public void AddNotice(Notice notice) throws Exception {
		try {
			dao.addNotice(notice);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("添加公告失败!");
		}
	}

	@Override
	/**
	 * 后台系统，修改公告
	 */
	public void EditNotice(Notice notice) throws Exception {
		try {
			dao.editNotice(notice);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
