package com.choimroc.mybatisplusdemo.common.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime
 *
 * @author choimroc
 * @since 2019/9/1
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        String dateFormatAsString = value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        out.value(dateFormatAsString);
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return LocalDateTime.parse(in.nextString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }
}
