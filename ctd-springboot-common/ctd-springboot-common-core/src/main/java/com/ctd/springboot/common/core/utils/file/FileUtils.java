package com.ctd.springboot.common.core.utils.file;

import com.ctd.springboot.common.core.exception.UnifiedException;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * FileUtils
 *
 * @author chentudong
 * @date 2020/8/22 15:15
 * @since 1.0
 */
public class FileUtils {

    /**
     * copy
     *
     * @param bytes bytes
     * @return Byte[]
     */
    public static Byte[] copy(byte[] bytes) {
        AssertUtils.isNull(bytes, "bytes 不能为空");
        int length = bytes.length;
        Byte[] cp = new Byte[length];
        for (int i = 0; i < length; i++) {
            cp[i] = bytes[i];
        }
        return cp;
    }

    /**
     * 返回uuid
     *
     * @return List<String>
     */
    public static List<String> uuids() {
        List<String> result = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        result.add(id);
        result.add(id.substring(0, 16));
        return result;
    }

    /**
     * 返回uuid
     *
     * @return String
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * uuidSubstring
     *
     * @param offset offset
     * @param length length
     * @return String
     */
    public static String uuidSubstring(int offset, int length) {
        return uuid().substring(offset, length);
    }

    /**
     * 添加文件
     *
     * @param path     path
     * @param fileName fileName
     * @param bytes    bytes
     */
    public static void addFile(String path, String fileName, Byte[] bytes) throws UnifiedException {
        if (StringUtils.isNotBlank(path) && StringUtils.isNotBlank(fileName) && Objects.nonNull(bytes)) {
            BufferedOutputStream out = null;
            try {
                File upload = new File(path.concat("/").concat(fileName));
                if (!upload.getParentFile().exists()) {
                    upload.getParentFile().mkdirs();
                }
                out = new BufferedOutputStream(new FileOutputStream(upload));
                for (Byte b : bytes) {
                    out.write(b);
                }
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
                AssertUtils.msgUser("网络异常， 请稍后重试。");
            } finally {
                if (Objects.nonNull(out)) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * newFileName
     *
     * @param path     path
     * @param fileName fileName
     * @param prefix   prefix
     * @return String
     */
    public static String newFileName(String path, String fileName, String prefix) {
        return path.concat("/").concat(newFileName(fileName, prefix));
    }

    /**
     * newFileName
     *
     * @param fileName fileName
     * @param prefix   prefix
     * @return String
     */
    public static String newFileName(String fileName, String prefix) {
        return fileName.replace("." + prefix, "").concat(uuidSubstring(0, 16)).concat(".").concat(prefix);
    }
}
