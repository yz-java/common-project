package com.yz.common.core.model;

import java.util.List;

/**
 * 分页模型
 * @author yangzhao
 * @Date create by 22:59 18/9/19
 */
public class Page<T> {
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 每页显示数量
     */
    private int pageSize;
    /**
     * 总页数
     */
    private long totalPage;
    /**
     * 分页数据
     */
    private List<T> dataList;

    public Page(){}

    public Page(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
