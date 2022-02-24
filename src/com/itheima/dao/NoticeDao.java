package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Notice;

public interface NoticeDao {

	List<Notice> findAll()throws Exception;

	Notice getRecentNotice()throws Exception;

	Notice findNoticeById(String n_id)throws Exception;

	void deleteNoticeById(String n_id)throws Exception;

	void addNotice(Notice notice)throws Exception;

	void editNotice(Notice notice)throws Exception;
	
}
