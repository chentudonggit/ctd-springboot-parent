package com.ctd.springboot.common.core.vo.file;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件
 *
 * @author chentudong
 * @date 2020/8/22 13:10
 * @since 1.0
 */
public class FileVO implements Serializable {
    private static final long serialVersionUID = 5619155027125528974L;

    /**
     * id
     */
    @ApiModelProperty("id")
    @JSONField(name = "id")
    @JsonProperty("id")
    private String id;

    /**
     * 原来的文件名
     */
    @ApiModelProperty("原来的文件名")
    @JSONField(name = "original_file_name")
    @JsonProperty("original_file_name")
    private String originalFileName;

    /**
     * 新文件名
     */
    @ApiModelProperty("新文件名")
    @JSONField(name = "new_file_name")
    @JsonProperty("new_file_name")
    private String newFileName;

    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    @JSONField(name = "file_size")
    @JsonProperty("file_size")
    private Long fileSize;

    /**
     * 上传时间
     */
    @ApiModelProperty("上传时间")
    @JSONField(name = "upload_time")
    @JsonProperty("upload_time")
    private Date uploadTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public String toString() {
        return "FileVO{" +
                "id='" + id + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", newFileName='" + newFileName + '\'' +
                ", fileSize=" + fileSize +
                ", uploadTime=" + uploadTime +
                '}';
    }
}
