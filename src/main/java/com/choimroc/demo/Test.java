package com.choimroc.demo;

import com.choimroc.demo.common.Result;
import com.choimroc.demo.common.gson.CollectionTypeAdapterFactory;
import com.choimroc.demo.common.gson.DoubleAdapter;
import com.choimroc.demo.common.gson.FloatAdapter;
import com.choimroc.demo.common.gson.IntegerAdapter;
import com.choimroc.demo.common.gson.LongAdapter;
import com.choimroc.demo.common.gson.StringAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author choimroc
 * @since 2019/3/8
 */
public class Test {
    public static void main(String[] args) {
        /*有效： "" {} [] 无效：null */
        String jsonStr = "{\"code\":200,\"msg\":\"success\",\"data\":[{\"id\":\"\",\"name\":\"Beijing\",\"cities\":null},{\"id\":2,\"name\":\"Guangdong\",\"cities\":[{\"id\":1,\"name\":\"Guangzhou\"}]},{},]}";
        GsonBuilder gsonBulder = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(int.class, new IntegerAdapter())
                .registerTypeAdapter(Integer.class, new IntegerAdapter())
                .registerTypeAdapter(float.class, new FloatAdapter())
                .registerTypeAdapter(Float.class, new FloatAdapter())
                .registerTypeAdapter(double.class, new DoubleAdapter())
                .registerTypeAdapter(Double.class, new DoubleAdapter())
                .registerTypeAdapter(long.class, new LongAdapter())
                .registerTypeAdapter(Long.class, new LongAdapter())
                .registerTypeAdapter(String.class, new StringAdapter());
//                .registerTypeAdapterFactory(
//                        new CollectionTypeAdapterFactory(
//                                new ConstructorConstructor(new HashMap<>())))
//                .create();

        try {
            Class builder = gsonBulder.getClass();
            Field f = builder.getDeclaredField("instanceCreators");
            f.setAccessible(true);
            Map<Type, InstanceCreator<?>> val = (Map<Type, InstanceCreator<?>>) f.get(gsonBulder);
            gsonBulder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(new ConstructorConstructor(val)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Gson gson = gsonBulder.create();
        Result<List<Province>> result = gson.fromJson(jsonStr, new TypeToken<Result<List<Province>>>() {
        }.getType());


        System.out.println(result.getData().get(2));
    }
}
