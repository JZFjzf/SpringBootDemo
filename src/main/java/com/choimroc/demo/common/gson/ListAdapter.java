package com.choimroc.demo.common.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * 将null替换成空List
 *
 * @author choimroc
 * @since 2018/11/4
 */
public class ListAdapter extends TypeAdapter<List<?>> {

    @Override
    public void write(JsonWriter out, List<?> value) throws IOException {
        out.value(String.valueOf(value));
    }

    @Override
    public List<?> read(JsonReader reader) throws IOException {
        if (reader.peek() != JsonToken.BEGIN_ARRAY) {
            reader.skipValue();
            return Collections.EMPTY_LIST;
        }
        return new Gson().fromJson(reader, new TypeToken<List<?>>(){}.getType());
    }
}
