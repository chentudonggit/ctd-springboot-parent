package com.ctd.springboot.common.core.vo.page;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * PageVO
 *
 * @author chentudong
 * @date 2020/3/7 12:14
 * @since 1.0
 */
public class PageVO<T> implements Serializable {
    private static final long serialVersionUID = -4315354068187132885L;

    /**
     * page
     *
     * @since 1.0
     */
    @JSONField(name = "page")
    private Integer page;
    /**
     * size
     */
    @JSONField(name = "size")
    private Integer size;

    /**
     * totalPage
     */
    @JsonProperty(value = "total_page")
    @JSONField(name = "total_page")
    private Integer totalPage;

    /**
     * totalCount
     */
    @JsonProperty(value = "total_count")
    @JSONField(name = "total_count")
    private Long totalCount;

    /**
     * result
     */
    @JSONField(name = "data")
    private List<T> data;

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

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageVO{" +
                "page=" + page +
                ", size=" + size +
                ", totalPage=" + totalPage +
                ", totalCount=" + totalCount +
                ", data=" + data +
                '}';
    }

    /**
     * 初始化分页对象
     *
     * @param page page
     * @param size size
     * @param <T>  <T>
     * @return Page<T>
     */
    public static <T> PageVO<T> init(Integer page, Integer size) {
        PageVO<T> result = new PageVO<>();
        result.setTotalCount(0L);
        result.setPage(Objects.isNull(page) ? 0 : size);
        result.setSize(Objects.isNull(size) ? 10 : size);
        result.setTotalPage(0);
        result.setData(new ArrayList<T>());
        return result;
    }
}
