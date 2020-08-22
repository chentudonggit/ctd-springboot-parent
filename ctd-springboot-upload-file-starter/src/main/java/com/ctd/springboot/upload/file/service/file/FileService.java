package com.ctd.springboot.upload.file.service.file;

import com.ctd.springboot.common.core.vo.file.FileVO;
import com.ctd.springboot.common.core.vo.file.request.RequestFileVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * FileService
 *
 * @author chentudong
 * @date 2020/8/22 14:00
 * @since 1.0
 */
@RequestMapping("/fileService")
public interface FileService {

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
    @RequestMapping(value = "upload")
    FileVO upload(@RequestParam("fileName") String fileName,
                  @RequestParam("prefix") String prefix,
                  @RequestParam("size") Long size,
                  @RequestParam("bytes") Byte[] bytes,
                  @RequestParam("path") String path);

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
    @RequestMapping(value = "uploadImage")
    FileVO uploadImage(@RequestParam("fileName") String fileName,
                       @RequestParam("prefix") String prefix,
                       @RequestParam("size") Long size,
                       @RequestParam("bytes") Byte[] bytes,
                       @RequestParam("path") String path);

    /**
     * 多个文件上传
     *
     * @param fileJsons fileJsons
     * @param path      path
     * @return List<FileVO>
     */
    @RequestMapping(value = "uploads")
    List<FileVO> upload(@RequestParam("path") String path, @RequestParam("files") String fileJsons);

    /**
     * 删除文件
     *
     * @param path      路径
     * @param fileNames 文件名称
     * @return Boolean
     */
    @RequestMapping("/deleteFile")
    Boolean deleteFile(@RequestParam("path") String path, @RequestParam("images") String[] fileNames);

    /**
     * 图片上传
     *
     * @param path  path
     * @param files files
     * @return List<FileVO>
     */
    @RequestMapping("/pathImages")
    List<FileVO> pathImages(@RequestParam("path") String path, @RequestBody RequestFileVO[] files);

    /**
     * 上传图片
     *
     * @param path  path
     * @param files files
     * @return List<FileVO>
     */
    @RequestMapping("pathMultipartImages")
    List<FileVO> pathMultipartImages(@RequestParam("path") String path, @RequestBody ArrayList<MultipartFile> files);

    /**
     * 上传文件
     *
     * @param path  path
     * @param files files
     * @return List<FileVO>
     */
    @RequestMapping("pathMultipartFiles")
    List<FileVO> pathMultipartFiles(@RequestParam("path") String path, @RequestBody ArrayList<MultipartFile> files);

    /**
     * 上传文件
     *
     * @param path path
     * @param file file
     * @return FileVO
     */
    @RequestMapping("pathMultipartFile")
    FileVO pathMultipartFile(@RequestParam("path") String path, @RequestBody MultipartFile file);

    /**
     * 上传图片
     *
     * @param path path
     * @param file file
     * @return FileVO
     */
    @RequestMapping("pathMultipartImage")
    FileVO pathMultipartImage(@RequestParam("path") String path, @RequestBody MultipartFile file);
}
