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
public class DoubleAdapter extends TypeAdapter<Number> {

    @Override
    public void write(JsonWriter out, Number value) throws IOException {
        if (value == null) {
            out.value(0D);
            return;
        }
        out.value(value);
    }

    public Number read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.skipValue();
            return 0D;
        } else if (reader.peek() == JsonToken.STRING) {
            try {
                return Integer.valueOf(reader.nextString());
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
                return 0D;
            }
        }
        return reader.nextDouble();
    }
}
