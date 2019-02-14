package org.moon.framework.core.utils.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 明月 on 2018-11-28 / 11:40
 *
 * @Description: 分页工具类
 */
public class Page<T> {

	/**
	 * Constant
	 */
	private static final int ZERO = 0B0;
	private static final int ONE = 0B1;
	private static final int PAGESIZE_MIN_THRESHOLD = ZERO;
	private static final int PAGESIZE_MAX_THRESHOLD = 100;
	private static final int DEFAULT_PAGESIZE = 0B11;
	private static final int DEFAULT_TOTAL_RECORDS = ZERO;

	// 当前页:
	private int currentPage;
	// 上一页:
	private int prePage;
	// 下一页:
	private int nextPage;
	// 总页数:
	private int totalPage;
	// 每页记录数:
	private int pageSize;
	// 总记录数据:
	private int totalRecords;
	// 开始记录索引:
	private int startIndex;
	// 当页数据
	private Collection<T> records;

	/**
	 * @param currentPage
	 *            当前页
	 * @param totalRecords
	 *            总记录数
	 */
	private Page(int currentPage, int pageSize, Collection<T> records) {
		// 当前页
		this.currentPage = currentPage <= ZERO ? ONE : currentPage;
		// 每页记录数
		if (pageSize <= PAGESIZE_MIN_THRESHOLD || pageSize > PAGESIZE_MAX_THRESHOLD) {
			pageSize = DEFAULT_PAGESIZE;
		}
		this.pageSize = pageSize;
		if (null != records) {
			// 总页数
			this.totalRecords = records.size();
			// 当页数据
			this.records = records;
		} else
			this.totalRecords = DEFAULT_TOTAL_RECORDS;
		// 开始记录索引
		this.startIndex = (this.currentPage - 1) * this.pageSize;
		// 总页数：
		this.totalPage = totalRecords % this.pageSize == 0 ? totalRecords / this.pageSize
				: totalRecords / this.pageSize + 1;
	}

	// 构建当前页
	public static <T> Page<T> create(int currentPage, int pageSize, Collection<T> records) {
		return new Page<T>(currentPage, pageSize, records);
	}

	// 上一页
	public int getPrePage() {
		prePage = currentPage == 1 ? currentPage : currentPage - 1;
		return prePage;
	}

	// 下一页
	public int getNextPage() {
		nextPage = currentPage == totalPage ? totalPage : currentPage + 1;
		return nextPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public Collection<T> getRecords() {
		return records;
	}

	public void addRecords(Collection<T> records) {
		if (null != records) {
			// 总记录数
			this.totalRecords = records.size();
			// 总页数
			this.totalPage = totalRecords % this.pageSize == 0 ? totalRecords / this.pageSize
					: totalRecords / this.pageSize + 1;
			this.records.clear();
			this.records.addAll(records);
		}
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", prePage=" + prePage + ", nextPage=" + nextPage + ", totalPage="
				+ totalPage + ", pageSize=" + pageSize + ", totalRecords=" + totalRecords + ", startIndex=" + startIndex
				+ ", records=" + records + "]";
	}

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			list.add("zs : " + i);
		}
		Page<String> page = Page.create(-1, -1, list);

		System.out.println(page);
		System.out.println(page.getPrePage());
		System.out.println(page.getNextPage());

		page.addRecords(new ArrayList<String>() {
			{
				add("wu");
				add("wu1");
				add("wu2");
			}
		});
		System.out.println(page);

	}
}