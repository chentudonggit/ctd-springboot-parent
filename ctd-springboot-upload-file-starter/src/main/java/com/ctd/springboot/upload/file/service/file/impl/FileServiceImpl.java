package com.ctd.springboot.upload.file.service.file.impl;

import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.utils.file.FileUtils;
import com.ctd.springboot.common.core.vo.file.FileVO;
import com.ctd.springboot.common.core.vo.file.request.RequestFileVO;
import com.ctd.springboot.upload.file.service.file.FileService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * FileServiceImpl
 *
 * @author chentudong
 * @date 2020/8/22 15:13
 * @since 1.0
 */
@RestController
public class FileServiceImpl implements FileService {
    /**
     * 单个文件上传
     *
     * @param fileName fileName
     * @param prefix   prefix
     * @param size     size
     * @param bytes    bytes
     * @param path     path
     * @return FileVO
     */
    @Override
    public FileVO upload(String fileName, String prefix, Long size, Byte[] bytes, String path) {
        isNull(fileName, prefix, path, bytes);
        FileVO file = new FileVO();
        file.setFileSize(size);
        file.setId(UUID.randomUUID().toString());
        file.setOriginalFileName(fileName);
        file.setNewFileName(fileName);
        FileUtils.addFile(path, fileName, bytes);
        return file;
    }

    /**
     * 图片上传
     *
     * @param fileName fileName
     * @param prefix   prefix
     * @param size     size
     * @param bytes    bytes
     * @param path     路径
     * @return FileVO
     */
    @Override
    public FileVO uploadImage(String fileName, String prefix, Long size, Byte[] bytes, String path) {
        return null;
    }

    /**
     * 多个文件上传
     *
     * @param path  path
     * @param files files
     * @return List<FileVO>
     */
    @Override
    public List<FileVO> upload(String path, String files) {
        return null;
    }

    /**
     * 删除文件
     *
     * @param path      路径
     * @param fileNames 文件名称
     * @return Boolean
     */
    @Override
    public Boolean deleteFile(String path, String[] fileNames) {
        return null;
    }

    /**
     * 按路径保存图片
     *
     * @param path       路径
     * @param imageJsons 图片json格式字符串
     * @return List<FileVO>
     */
    @Override
    public List<FileVO> pathImages(String path, String[] imageJsons) {
        return null;
    }

    /**
     * 图片上传
     *
     * @param path  path
     * @param files files
     * @return List<FileVO>
     */
    @Override
    public List<FileVO> pathImages(String path, ArrayList<RequestFileVO> files) {
        return null;
    }

    /**
     * 上传图片
     *
     * @param path  path
     * @param files files
     * @return List<FileVO>
     */
    @Override
    public List<FileVO> pathMultipartImages(String path, ArrayList<MultipartFile> files) {
        return null;
    }

    /**
     * 上传文件
     *
     * @param path  path
     * @param files files
     * @return List<FileVO>
     */
    @Override
    public List<FileVO> pathMultipartFile(String path, ArrayList<MultipartFile> files) {
        return null;
    }

    private void isNull(String fileName, String prefix, String path, Byte[] bytes) {
        AssertUtils.isNull(fileName, "fileName 不能为空");
        AssertUtils.isNull(prefix, "prefix 不能为空");
        AssertUtils.isNull(path, "path 不能为空");
        AssertUtils.isNull(bytes, "bytes 不能为空");
    }
}
