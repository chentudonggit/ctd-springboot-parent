package com.ctd.springboot.common.core.vo.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * SearchVO
 *
 * @author chentudong
 * @date 2020/5/4 2:17
 * @since 1.0
 */
@ApiModel("SearchVO")
public class SearchVO implements Serializable {
    /**
     * page
     */
    @ApiModelProperty("当前页")
    private Integer page;

    /**
     * size
     */
    @ApiModelProperty("每页显示多少条数据")
    private Integer size;

    /**
     * where
     */
    @ApiModelProperty("查询条件")
    private Map<String, Object> where;

    /**
     * orderBy
     */
    @ApiModelProperty("排序")
    private Map<String, String> orderBy;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Map<String, Object> getWhere() {
        return where;
    }

    public void setWhere(Map<String, Object> where) {
        this.where = where;
    }

    public Map<String, String> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Map<String, String> orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public String toString() {
        return "SearchVO{" +
                "page=" + page +
                ", size=" + size +
                ", where=" + where +
                ", orderBy=" + orderBy +
                '}';
    }
}
