package com.choimroc.demo.common.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * 将null,""替换成0
 *
 * @author choimroc
 * @since 2018/11/4
 */
public class LongAdapter extends TypeAdapter<Long> {

    @Override
    public void write(JsonWriter out, Long value) throws IOException {
        if (value == null) {
            out.value(0L);
            return;
        }
        out.value(value);
    }

    public Long read(JsonReader reader) throws IOException {
        if (reader.peek() != JsonToken.NUMBER) {
            reader.skipValue();
            return 0L;
        }
        return reader.nextLong();
    }
}
