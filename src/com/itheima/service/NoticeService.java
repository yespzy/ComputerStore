package com.itheima.service;

import java.util.List;

import com.itheima.domain.Notice;

public interface NoticeService {

	List<Notice> findList()throws Exception;

	Notice getRecentNotice()throws Exception;

	Notice EditNoticeById(String n_id)throws Exception;

	void DeleteNoticeById(String n_id)throws Exception;

	void AddNotice(Notice notice)throws Exception;

	void EditNotice(Notice notice)throws Exception;

}
