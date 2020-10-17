package com.ctd.springboot.cloud.format.date;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * CustomLocalDateStringConverter
 *
 * @author chentudong
 * @date 2020/10/12 20:10
 * @since 1.0
 */
public class CustomLocalDateStringConverter implements Converter<LocalDate, String> {
    @Override
    public String convert(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
