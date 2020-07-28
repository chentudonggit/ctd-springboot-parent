package com.ctd.springboot.common.core.vo.result.page;

import com.alibaba.fastjson.annotation.JSONField;
import com.ctd.springboot.common.core.enums.code.CodeEnum;
import com.ctd.springboot.common.core.vo.page.PageVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * PageResultVO
 *
 * @author chentudong
 * @date 2020/7/28 13:49
 * @since 1.0
 */
public class PageResultVO<T> implements Serializable {
    private static final long serialVersionUID = 3812294267200953136L;
    /**
     * data
     */
    @ApiModelProperty("data")
    private List<T> data;

    /**
     * code
     */
    @ApiModelProperty("状态码")
    private Integer code;

    /**
     * message
     */
    @ApiModelProperty("提示信息")
    private String message;

    /**
     * page
     *
     * @since 1.0
     */
    @ApiModelProperty("page")
    @JSONField(name = "page")
    private Integer page;
    /**
     * size
     */
    @ApiModelProperty("size")
    @JSONField(name = "size")
    private Integer size;

    /**
     * totalPage
     */
    @ApiModelProperty("total_page")
    @JsonProperty(value = "total_page")
    @JSONField(name = "total_page")
    private Integer totalPage;

    /**
     * totalCount
     */
    @ApiModelProperty("total_count")
    @JsonProperty(value = "total_count")
    @JSONField(name = "total_count")
    private Long totalCount;

    /**
     * 成功/失败
     */
    @ApiModelProperty("success")
    @JsonProperty(value = "success")
    @JSONField(name = "success")
    private Boolean success;

    public PageResultVO() {
    }

    public PageResultVO(List<T> data, Integer code, String message, Boolean success) {
        this.data = data;
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public PageResultVO(List<T> data, Integer code, String message, Integer page, Integer size, Integer totalPage, Long totalCount, Boolean success) {
        this.data = data;
        this.code = code;
        this.message = message;
        this.page = page;
        this.size = size;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.success = success;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static <T> PageResultVO<T> succeedWith(List<T> data, Integer page, Integer size, Integer totalPage, Long totalCount) {
        return new PageResultVO(data, CodeEnum.SUCCESS.code(), CodeEnum.SUCCESS.message(),
                page, size, totalPage, totalCount, true);
    }

    public static <T> PageResultVO<T> succeed(PageVO page) {
        return succeedWith(page.getData(), page.getPage(), page.getSize(), page.getTotalPage(), page.getTotalCount());
    }

    public static <T> PageResultVO<T> failed(String msg) {
        return failedWith(CodeEnum.SERVER_ERROR.getCode(), msg);
    }

    public static <T> PageResultVO<T> failedWith(Integer code, String msg) {
        return new PageResultVO<>(null, code, msg, false);
    }

    @Override
    public String toString() {
        return "PageResultVO{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", page=" + page +
                ", size=" + size +
                ", totalPage=" + totalPage +
                ", totalCount=" + totalCount +
                ", success=" + success +
                '}';
    }
}
