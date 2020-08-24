package com.ctd.springboot.common.core.utils.image;

import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.utils.file.FileUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * ImageUtils
 *
 * @author chentudong
 * @date 2020/8/22 12:58
 * @since 1.0
 */
public class ImageUtils {
    private static final Integer MAX_SIZE = 200000;
    private static final Integer K500 = 500000;
    private static final Integer M1 = 1024000;
    private static final String JPG = "jpg";
    private static final String JPEG = "jpeg";
    private static final String PNG = "png";
    private static final String GIF = "gif";

    /**
     * 输出流
     */
    private static ByteArrayOutputStream OUT = new ByteArrayOutputStream();

    /**
     * 图片压缩
     *
     * @param img     img
     * @param scale   scale
     * @param quality 参数是浮点数，0-1之间
     * @return byte[]    返回类型
     */
    public static byte[] compressFile(InputStream img, float scale, float quality, String outFilePath) {
        try {
            // 图片处理
            Builder<? extends InputStream> builder = Thumbnails.of(img).scale(scale).outputQuality(quality);
            // 判断是否指定存储的路径
            if (StringUtils.isNotBlank(outFilePath)) {
                builder.toFile(outFilePath);
                return null;
            }
            // 重置输出流
            OUT.reset();
            //将图片压缩
            builder.toOutputStream(OUT);
            return OUT.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 指定大小进行缩放
     *
     * @param img         img
     * @param width       width
     * @param height      height
     * @param outFilePath outFilePath
     * @param flag        keepAspectRatio(true)，将按比例进行缩放，否则将强制按尺寸输出
     * @return byte[]    返回类型
     */
    public static byte[] shrinkToSize(InputStream img, int width, int height, String outFilePath, boolean flag) {
        try {
            //图片处理
            Builder<? extends InputStream> builder = Thumbnails.of(img).size(width, height).keepAspectRatio(flag);
            //判断是否指定存储的路径
            if (StringUtils.isNotBlank(outFilePath)) {
                builder.toFile(outFilePath);
                return null;
            }
            //重置输出流
            OUT.reset();
            //图片处理
            builder.toOutputStream(OUT);
            return OUT.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 按照比例进行缩放
     *
     * @param img         InputStream
     * @param scale       参数是浮点数,大于1表示放大，小于1表示缩小
     * @param outFilePath 设定文件
     * @return byte[]    返回类型
     */
    public static byte[] shrinkToScale(InputStream img, double scale, String outFilePath) {
        try {
            // 图片处理
            Builder<? extends InputStream> builder = Thumbnails.of(img).scale(scale);
            //判断是否指定存储的路径
            if (StringUtils.isNotBlank(outFilePath)) {
                builder.toFile(outFilePath);
                return null;
            }
            // 返回处理的数据
            OUT.reset();
            builder.toOutputStream(OUT);
            return OUT.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 水印
     *
     * @param img          img
     * @param width        width
     * @param height       height
     * @param watermarkIMG watermarkIMG
     * @param outFilePath  outFilePath
     * @return byte[]    返回类型
     */
    public static byte[] addWatermark(InputStream img, int width, int height, String watermarkIMG, String outFilePath) {
        // watermark(位置，水印图，透明度)
        try {
            Builder<? extends InputStream> builder = Thumbnails.of(img).size(width, height)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(watermarkIMG)), 0.5f).outputQuality(0.8f);

            if (StringUtils.isNotBlank(outFilePath)) {
                builder.toFile(outFilePath);
                return null;
            }
            OUT.reset();
            builder.toOutputStream(OUT);
            return OUT.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 水印
     *
     * @param img          img
     * @param watermarkIMG watermarkIMG
     * @return BufferedImage    返回类型
     */
    public static byte[] addWatermark(InputStream img, String watermarkIMG, String outFilePath) {
        // watermark(位置，水印图，透明度)
        try {
            Builder<? extends InputStream> builder = Thumbnails.of(img).scale(1f).watermark(Positions.TOP_RIGHT,
                    ImageIO.read(new File(watermarkIMG)), 0.5f).outputQuality(0.8f);

            if (StringUtils.isNotBlank(outFilePath)) {
                builder.toFile(outFilePath);
                return null;
            }
            OUT.reset();
            builder.toOutputStream(OUT);
            return OUT.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 旋转图片
     *
     * @param img         img
     * @param scale       scale
     * @param angle       angle
     * @param outFilePath 设定文件
     */
    public static byte[] retateImg(InputStream img, double scale, double angle, String outFilePath) {
        try {
            Builder<? extends InputStream> builder = Thumbnails.of(img).scale(scale).rotate(angle);

            if (StringUtils.isNotBlank(outFilePath)) {
                builder.toFile(outFilePath);
                return null;
            }
            OUT.reset();
            builder.toOutputStream(OUT);
            return OUT.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 图片格式转换
     *
     * @param img         img
     * @param width       width
     * @param height      height
     * @param outFilePath 设定文件
     */
    public static byte[] imageConversion(InputStream img, int width, int height, String suffixName, String outFilePath) {
        try {
            //判断后缀名是否合法
            if (!(suffixName.endsWith(PNG) || suffixName.endsWith(JPG) || suffixName.endsWith(JPEG) || suffixName.endsWith(GIF))) {
                AssertUtils.msgUser("%s, 不是常见的图片的类型, 请使用：%s,%s,%s,%s", suffixName, PNG, JPEG, JPG, GIF);
            }

            Builder<? extends InputStream> outputFormat = Thumbnails.of(img).size(width, height).outputFormat(suffixName);
            if (StringUtils.isNotBlank(outFilePath)) {
                outputFormat.toFile(outFilePath);
                return null;
            }
            OUT.reset();
            outputFormat.toOutputStream(OUT);
            return OUT.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 图片压缩， 超过200KB 以上才压缩
     *
     * @param file     文件
     * @param outPath  输出路径
     * @param fileName 文件名
     */
    public static void contraction(MultipartFile file, String outPath, String fileName) {
        AssertUtils.isNull(file, "file不能为空");
        try {
            long size = file.getSize();
            if (size > MAX_SIZE) {
                double scale = proportion(size);
                shrinkToScale(file.getInputStream(), scale, outPath + "/" + fileName);
            } else {
                Files.copy(file.getInputStream(), Paths.get(outPath, fileName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 比例
     *
     * @param size size
     * @return double
     */
    public static double proportion(long size) {
        double scale = 0.7;
        if (size > M1) {
            scale = 0.4;
        } else if (size > K500) {
            scale = 0.5;
        }
        return scale;
    }

    /**
     * 是否草超过上限
     *
     * @param size size
     * @return boolean
     */
    public static boolean isMax(long size) {
        return size > MAX_SIZE;
    }

    /**
     * 判断后缀名
     *
     * @param prefix prefix
     */
    public static void isPrefix(String prefix) {
        AssertUtils.isNull(prefix, "后缀名不能为空");
        if (!prefix.equalsIgnoreCase(JPEG) && !prefix.equalsIgnoreCase(JPG) && !prefix.equalsIgnoreCase(PNG) && !prefix.equalsIgnoreCase(GIF)) {
            AssertUtils.msgUser("文件格式不正确， 只支持：%s,%s,%s,%s,", JPEG, JPG, PNG, GIF);
        }
    }

    /**
     * getByte
     *
     * @param size size
     * @param file file
     * @return Byte[]
     */
    public static Byte[] shrinkToScale(long size, MultipartFile file) {
        Byte[] bytes = null;
        try {
            if (isMax(size)) {
                //添加压缩
                bytes = FileUtils.copy(shrinkToScale(file.getInputStream(), proportion(size), null));
            } else {
                bytes = FileUtils.copy(file.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
