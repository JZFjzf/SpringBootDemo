package com.choimroc.demo;

import com.choimroc.demo.common.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author choimroc
 * @since 2019/3/8
 */
public class Test {
    public static void main(String[] args) {
        /*有效： "" {} [] 无效：null */
        String jsonStr = "{\"code\":200,\"msg\":\"\",\"data\":[\"msg\",\"msg\",\"msg\"]}";
//        String jsonStr = "{\"code\":200,\"msg\":\"\",\"data\":null}";
        Gson gson = new GsonBuilder()
                .serializeNulls()
//                .registerTypeHierarchyAdapter(List.class, new ListAdapter())
                .create();


        Result<List<String>> result = gson.fromJson(jsonStr, new TypeToken<Result<List<String>>>() {
        }.getType());

        System.out.println(result.getData());
    }

    static class GsonListAdapter implements JsonDeserializer<List<?>> {
        @Override
        public List<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonArray()) {
                JsonArray array = json.getAsJsonArray();
                Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
                List list = new ArrayList<>();
                for (int i = 0; i < array.size(); i++) {
                    JsonElement element = array.get(i);
                    Object item = context.deserialize(element, itemType);
                    list.add(item);
                }
                return list;
            } else {
                return Collections.EMPTY_LIST;
            }
        }
    }

    static class ListAdapter extends TypeAdapter<List<?>> {

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
            return new Gson().fromJson(reader, ArrayList.class);
        }
    }
}
