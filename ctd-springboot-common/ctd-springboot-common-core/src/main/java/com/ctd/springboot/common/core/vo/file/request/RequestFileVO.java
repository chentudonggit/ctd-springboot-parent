package com.ctd.springboot.common.core.vo.file.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * RequestFileVO
 *
 * @author chentudong
 * @date 2020/8/22 13:10
 * @since 1.0
 */
public class RequestFileVO implements Serializable {
    private static final long serialVersionUID = -8706523628778394549L;
    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    @JSONField(name="file_name")
    @JsonProperty("file_name")
    private String fileName;

    /**
     * 后缀名
     */
    @ApiModelProperty("后缀名")
    @JSONField(name="prefix")
    @JsonProperty("prefix")
    private String prefix;

    /**
     * 大小
     */
    @ApiModelProperty("大小")
    @JSONField(name="size")
    @JsonProperty("size")
    private Long size;

    /**
     * 文件内容
     */
    @ApiModelProperty("文件内容")
    @JSONField(name="bytes")
    @JsonProperty("bytes")
    private Byte[] bytes;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Byte[] getBytes() {
        return bytes;
    }

    public void setBytes(Byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return "RequestFileVO{" +
                "fileName='" + fileName + '\'' +
                ", prefix='" + prefix + '\'' +
                ", size=" + size +
                ", bytes=" + Arrays.toString(bytes) +
                '}';
    }
}
