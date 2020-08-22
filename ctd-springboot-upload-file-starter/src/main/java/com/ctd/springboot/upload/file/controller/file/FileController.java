package com.ctd.springboot.upload.file.controller.file;

import com.ctd.springboot.common.core.vo.file.FileVO;
import com.ctd.springboot.common.core.vo.result.ResultVO;
import com.ctd.springboot.upload.file.service.file.FileService;
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
}
