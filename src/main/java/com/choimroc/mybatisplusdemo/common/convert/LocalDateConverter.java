package com.choimroc.mybatisplusdemo.common.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The type Local date converter.
 *
 * @author choimroc
 * @since 2019 /10/5
 */
@Component
public class LocalDateConverter implements Converter<String, LocalDate> {
    private static final String format = "yyyy-MM-dd";

    @Override
    public LocalDate convert(String source) {
        System.out.println(source);
        String value = source.trim();
        if ("".equals(value)) {
            return null;
        }
        if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return parseDate(source);
        } else {
            throw new IllegalArgumentException(
                    String.format("Invalid LocalDate value '%s'.The format must be '%s'.", source, format));
        }
    }


    private LocalDate parseDate(String dateStr) {
        return LocalDate.from(DateTimeFormatter.ofPattern(LocalDateConverter.format).parse(dateStr));
    }
}
