package com.ctd.springboot.upload.file.controller.file;

import com.ctd.springboot.common.core.vo.file.FileVO;
import com.ctd.springboot.common.core.vo.result.ResultVO;
import com.ctd.springboot.upload.file.service.file.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileController
 *
 * @author chentudong
 * @date 2020/8/22 17:45
 * @since 1.0
 */
@Api(tags = "文件上传")
@RestController
@RequestMapping("file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 上传图片
     *
     * @param path path
     * @param file file
     * @return ResultVO<FileVO>
     */
    @ApiOperation("上传图片")
    @PostMapping("uploadImage")
    public ResultVO<FileVO> uploadImage(@RequestParam("path") String path, @RequestBody MultipartFile file) {
        return ResultVO.succeed(fileService.pathMultipartImage(path, file));
    }

    /**
     * uploadFile
     *
     * @param path     path
     * @param fileName fileName
     * @param file     file
     * @return ResultVO<FileVO>
     */
    @ApiOperation("上传文件")
    @PostMapping("uploadFile")
    public ResultVO<FileVO> uploadFile(@RequestParam("path") String path,
                                       @RequestParam("fileName") String fileName,
                                       @RequestBody MultipartFile file) {
        return ResultVO.succeed(fileService.pathMultipartFile(path, fileName, file));
    }

    /**
     * delete
     *
     * @param path     path
     * @param fileName fileName
     * @return
     */
    @ApiOperation("删除文件")
    @DeleteMapping("delete")
    public ResultVO<Boolean> delete(@RequestParam("path") String path, @RequestParam("fileName") String fileName) {
        return ResultVO.succeed(fileService.deleteFile(path, fileName));
    }

    /**
     * deletes
     *
     * @param path      path
     * @param fileNames fileNames
     * @return
     */
    @ApiOperation("删除文件")
    @DeleteMapping("deletes")
    public ResultVO<Boolean> deletes(@RequestParam("path") String path, @RequestBody String[] fileNames) {
        return ResultVO.succeed(fileService.deleteFiles(path, fileNames));
    }
}
