package com.ctd.springboot.cloud.format.date;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * DateFormatter
 *
 * @author chentudong
 * @date 2020/3/7 22:05
 * @since 1.0
 */
public class DateFormatter implements Formatter<Date>
{
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @Override
    public Date parse(String text, Locale locale) throws ParseException
    {
        return formatter.parse(text);
    }

    @Override
    public String print(Date date, Locale locale)
    {
        return formatter.format(date);
    }
}
