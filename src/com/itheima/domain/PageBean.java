package com.itheima.domain;

import java.util.List;

public class PageBean<T> {
	private List<T> data;//当前页的数据	
	private int pageNumber;//当前页	
	private int totalRecord;//总条数	
	private int pageSize;//每页显示的数量	
	private int totalPage;//总页数			
	private String category;//类别
	private String searchfield;//模糊搜索的商品名
	
	public List<T> getData() {
		return data;
	}
	
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public int getTotalRecord() {
		return totalRecord;
	}
	
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return (int)Math.ceil(totalRecord*1.0/pageSize);
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getSearchfield() {
		return searchfield;
	}

	public void setSearchfield(String searchfield) {
		this.searchfield = searchfield;
	}
	
	public int getStartIndex(){
		return (pageNumber-1)*pageSize;
	}
	
	public PageBean(int pageNumber, int pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	
	public PageBean(int currentPage, int currentCount, String searchfield) {
		super();
		this.pageNumber = currentPage;
		this.pageSize = currentCount;
		this.searchfield=searchfield;
	}

}
