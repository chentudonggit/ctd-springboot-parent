package com.ctd.springboot.cloud.format.date;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * CustomLocalDateTimeStringConverter
 *
 * @author chentudong
 * @date 2020/10/12 20:07
 * @since 1.0
 */
public class CustomLocalDateTimeStringConverter implements Converter<LocalDateTime, String> {
    @Override
    public String convert(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
