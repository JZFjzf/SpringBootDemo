package com.choimroc.demo;

import com.choimroc.demo.common.Result;
import com.choimroc.demo.common.gson.CollectionTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author choimroc
 * @since 2019/3/8
 */
public class Test {
    public static void main(String[] args) {
        /*有效： "" {} [] 无效：null */
        String jsonStr = "{\"code\":200,\"msg\":\"success\",\"data\":[{\"id\":1,\"name\":\"Beijing\",\"cities\":null},{\"id\":2,\"name\":\"Guangdong\",\"cities\":[{\"id\":1,\"name\":\"Guangzhou\"}]}]}";
//        String jsonStr = "{\"code\":200,\"msg\":\"\",\"data\":[\"msg\",\"msg\",\"msg\"]}";
//        String jsonStr = "{\"code\":200,\"msg\":\"\",\"data\":null}";
        GsonBuilder gsonBulder = new GsonBuilder()
                .serializeNulls();

        try {
            Class builder = (Class) gsonBulder.getClass();
            Field f = builder.getDeclaredField("instanceCreators");
            f.setAccessible(true);
            Map<Type, InstanceCreator<?>> val = (Map<Type, InstanceCreator<?>>) f.get(gsonBulder);//得到此属性的值
            //注册数组的处理器
            gsonBulder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(new ConstructorConstructor(val)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

//        gsonBulder.registerTypeAdapterFactory(ListAdapter.FACTORY);
      Gson gson = gsonBulder.create();

//        Result<List<String>> result = gson.fromJson(jsonStr, new TypeToken<Result<List<String>>>() {
//        }.getType());

//        List<Province> provinces = new ArrayList<>();
//        List<City> cities = new ArrayList<>();
//        cities.add(new City(1, "Guangzhou"));
//        provinces.add(new Province(1, "Beijing", new ArrayList<>()));
//        provinces.add(new Province(2, "Guangdong", cities));

//        Result<List<Province>> result = new Result<>();
//        result.setCode(200);
//        result.setMsg("success");
//        result.setData();

        Result<List<Province>> result = gson.fromJson(jsonStr, new TypeToken<List<Province>>(){}.getType());


//        Province.City option = listResult.getData().get(0).getCities().get(0);

        System.out.println(result.getData());
//        System.out.println(new Gson().toJson(provinces));
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

//    static class ListAdapter extends TypeAdapter<List<?>> {
//
//        @Override
//        public void write(JsonWriter out, List<?> value) throws IOException {
//            out.value(String.valueOf(value));
//        }
//
//        @Override
//        public List<?> read(JsonReader reader) throws IOException {
//            if (reader.peek() != JsonToken.BEGIN_ARRAY) {
//                reader.skipValue();
//                return Collections.EMPTY_LIST;
//            }
//            return new Gson().fromJson(reader, ArrayList.class);
//        }
//    }
}
