package com.ctd.springboot.upload.file.service.file.impl;

import com.alibaba.fastjson.JSON;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.utils.file.FileUtils;
import com.ctd.springboot.common.core.utils.image.ImageUtils;
import com.ctd.springboot.common.core.vo.file.FileVO;
import com.ctd.springboot.common.core.vo.file.request.RequestFileVO;
import com.ctd.springboot.upload.file.service.file.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    public FileVO upload(String fileName, String prefix, Long size, Byte[] bytes, String path, Boolean isNewFileName) {
        isNull(fileName, prefix, path, bytes);
        FileVO file = FileUtils.initFileVO(size, fileName, prefix, isNewFileName);
        FileUtils.addFile(path, file.getNewFileName(), bytes);
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
        isNull(fileName, prefix, path, bytes);
        ImageUtils.isPrefix(prefix);
        return upload(fileName, prefix, size, bytes, path, true);
    }

    /**
     * 多个文件上传
     *
     * @param path      path
     * @param fileJsons fileJsons
     * @return List<FileVO>
     */
    @Override
    public List<FileVO> upload(String path, String fileJsons) {
        AssertUtils.isNull(fileJsons, "fileJsons 不能为空");
        RequestFileVO[] files = JSON.parseObject(fileJsons, RequestFileVO[].class);
        return pathImages(path, files);
    }

    /**
     * 删除文件
     *
     * @param path      路径
     * @param fileNames 文件名称
     * @return Boolean
     */
    @Override
    public Boolean deleteFiles(String path, String[] fileNames) {
        try {
            FileUtils.delete(path, fileNames);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param path     路径
     * @param fileName 文件名称
     * @return Boolean
     */
    @Override
    public Boolean deleteFile(String path, String fileName) {
        try {
            FileUtils.delete(path, fileName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 图片上传
     *
     * @param path  path
     * @param files files
     * @return List<FileVO>
     */
    @Override
    public List<FileVO> pathImages(String path, RequestFileVO[] files) {
        AssertUtils.isNull(path, "path 不能为空");
        AssertUtils.isNull(files, "files 不能为空");
        List<FileVO> result = new ArrayList<>(files.length);
        for (RequestFileVO file : files) {
            result.add(upload(file.getFileName(), file.getPrefix(), file.getSize(), file.getBytes(), path, true));
        }
        return result;
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
        AssertUtils.isNull(path, "path 不能为空");
        AssertUtils.isNullToUser(files, "请选择文件");
        List<FileVO> result = new ArrayList<>(files.size());
        for (MultipartFile file : files) {
            result.add(pathMultipartImage(path, file));
        }
        return result;
    }


    /**
     * 上传文件
     *
     * @param path path
     * @param file file
     * @return FileVO
     */
    @Override
    public FileVO pathMultipartFile(String path, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            return upload(fileName, FilenameUtils.getExtension(fileName), file.getSize(), FileUtils.copy(file.getBytes()), path, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @param path     path
     * @param fileName fileName
     * @param file     file
     * @return FileVO
     */
    @Override
    public FileVO pathMultipartFile(String path, String fileName, MultipartFile file) {
        try {
            return upload(fileName, FilenameUtils.getExtension(file.getOriginalFilename()), file.getSize(), FileUtils.copy(file.getBytes()), path, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传图片
     *
     * @param path path
     * @param file file
     * @return FileVO
     */
    @Override
    public FileVO pathMultipartImage(String path, MultipartFile file) {
        AssertUtils.isNullToUser(file, "请选择文件");
        String fileName = file.getOriginalFilename();
        try {
            long size = file.getSize();
            return uploadImage(fileName, FilenameUtils.getExtension(fileName), size, ImageUtils.shrinkToScale(size, file), path);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public List<FileVO> pathMultipartFiles(String path, ArrayList<MultipartFile> files) {
        AssertUtils.isNull(path, "path 不能为空");
        AssertUtils.isNullToUser(files, "请选择文件");
        List<FileVO> result = new ArrayList<>(files.size());
        for (MultipartFile file : files) {
            result.add(pathMultipartFile(path, file));
        }
        return result;
    }

    private void isNull(String fileName, String prefix, String path, Byte[] bytes) {
        AssertUtils.isNull(fileName, "fileName 不能为空");
        AssertUtils.isNull(prefix, "prefix 不能为空");
        AssertUtils.isNull(path, "path 不能为空");
        AssertUtils.isNull(bytes, "bytes 不能为空");
    }
}
