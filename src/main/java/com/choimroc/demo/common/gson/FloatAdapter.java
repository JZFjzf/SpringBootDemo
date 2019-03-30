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
public class FloatAdapter extends TypeAdapter<Float> {
    @Override
    public void write(JsonWriter out, Float value) throws IOException {
        if (value == null) {
            out.value(0F);
            return;
        }
        out.value(value);
    }

    public Float read(JsonReader reader) throws IOException {
        if (reader.peek() != JsonToken.NUMBER) {
            reader.skipValue();
            return 0F;
        }
        return (float) reader.nextDouble();
    }
}
