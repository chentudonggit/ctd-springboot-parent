package com.ctd.springboot.common.core.utils.excle;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * ExcelUtils
 *
 * @author chentudong
 * @date 2020/10/10 11:23
 * @since 1.0
 */
public class ExcelUtils {

    /**
     * 导出excel
     *
     * @param data           data
     * @param title          title
     * @param sheetName      sheetName
     * @param pojoClass      pojoClass
     * @param fileName       fileName
     * @param isCreateHeader isCreateHeader
     * @param response       response
     */
    public static void exportExcel(List<?> data, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(data, pojoClass, fileName, response, exportParams);

    }

    /**
     * 导出excel
     *
     * @param data      data
     * @param title     title
     * @param sheetName sheetName
     * @param pojoClass pojoClass
     * @param fileName  fileName
     * @param response  response
     */
    public static void exportExcel(List<?> data, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(data, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * 导出excel
     *
     * @param data     data
     * @param fileName fileName
     * @param response response
     */
    public static void exportExcel(List<Map<String, Object>> data, String fileName, HttpServletResponse response) {
        defaultExport(data, fileName, response);
    }

    /**
     * defaultExport
     *
     * @param data         data
     * @param pojoClass    pojoClass
     * @param fileName     fileName
     * @param response     response
     * @param exportParams exportParams
     */
    private static void defaultExport(List<?> data, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, data);
        if (Objects.nonNull(workbook)) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * downLoadExcel
     *
     * @param fileName fileName
     * @param response response
     * @param workbook workbook
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * defaultExport
     *
     * @param data     data
     * @param fileName fileName
     * @param response response
     */
    private static void defaultExport(List<Map<String, Object>> data, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(data, ExcelType.HSSF);
        if (Objects.nonNull(workbook)) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * importExcel
     *
     * @param filePath   filePath
     * @param titleRows  titleRows
     * @param headerRows headerRows
     * @param pojoClass  pojoClass
     * @param <T>        <T>
     * @return List<T>
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    /**
     * importExcel
     *
     * @param file       file
     * @param titleRows  titleRows
     * @param headerRows headerRows
     * @param pojoClass  pojoClass
     * @param <T>        <T>
     * @return List<T>
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (Objects.isNull(file)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }
}
