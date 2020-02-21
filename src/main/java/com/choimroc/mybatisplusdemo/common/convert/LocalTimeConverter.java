package com.choimroc.mybatisplusdemo.common.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Local time converter.
 *
 * @author choimroc
 * @since 2019 /10/5
 */
@Component
public class LocalTimeConverter implements Converter<String, LocalTime> {
    private static final String[] formats = new String[]{"HH:mm", "HH:mm:ss"};

    @Override
    public LocalTime convert(String source) {
        String value = source.trim();
        if ("".equals(value)) {
            return null;
        }
        if (source.matches("\\d{1,2}:\\d{1,2}$")) {
            return parseDate(source, formats[0]);
        } else if (source.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(source, formats[1]);
        } else {
            throw new IllegalArgumentException(
                    String.format("Invalid LocalTime value '%s'.The format must be '%s' or '%s'.",
                            source, formats[0], formats[1]));
        }
    }

    private LocalTime parseDate(String dateStr, String format) {
        return LocalTime.from(DateTimeFormatter.ofPattern(format).parse(dateStr));
    }
}
