package com.r2d2.common.web;

import java.util.List;

/**
 Created by Zhikun on 25/11/2017. */
public class RestPager<T> {

    private int currentPage = 1;
    private int pageSize = 10;
    private int pageTotal;
    private int recordTotal = 0;
    private int previousPage;
    private int nextPage;
    private int firstPage = 1;
    private int lastPage;
    private List<T> content;


    public void refreshAttr() {
        // 总页数
        this.pageTotal = this.recordTotal % this.pageSize > 0 ? this.recordTotal / this.pageSize + 1 : this.recordTotal / this.pageSize;
        // 第一页
        this.firstPage = 1;
        // 最后一页
        this.lastPage = this.pageTotal;
        // 前一页
        if (this.currentPage > 1) {
            this.previousPage = this.currentPage - 1;
        } else {
            this.previousPage = this.firstPage;
        }
        // 下一页
        if (this.currentPage < this.lastPage) {
            this.nextPage = this.currentPage + 1;
        } else {
            this.nextPage = this.lastPage;
        }
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
        refreshAttr();
    }

    public int getPageTotal() {
        return pageTotal;
    }


    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
        refreshAttr();
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
