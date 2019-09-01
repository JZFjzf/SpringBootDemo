package com.choimroc.demo.common.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gson 工具
 *
 * @author choimroc
 * @since 2019/3/8
 */
public class GsonHelper {

    private static GsonHelper gsonHelper = null;

    public static GsonHelper getInstance() {
        if (gsonHelper != null) {
            return gsonHelper;
        }
        return new GsonHelper();
    }

    public <T> List<T> fromList(String jsonArray, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return createGson().fromJson(jsonArray, typeOfT);
    }

    public Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
//                .registerTypeAdapterFactory(TypeAdapters.newFactory(int.class, Integer.class, new IntegerAdapter()))
//                .registerTypeAdapterFactory(TypeAdapters.newFactory(float.class, Float.class, new FloatAdapter()))
//                .registerTypeAdapterFactory(TypeAdapters.newFactory(double.class, Double.class, new DoubleAdapter()))
//                .registerTypeAdapterFactory(TypeAdapters.newFactory(long.class, Long.class, new LongAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(String.class, new StringAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(LocalDateTime.class, new DateAdapter()))
                .registerTypeAdapterFactory(createCollectionTypeAdapterFactory(gsonBuilder, false));

        return gsonBuilder.create();
    }


    /**
     * 如果有使用Gson的InstanceCreator,如
     * registerTypeAdapter(Bean.class,new InstanceCreator<Base>(){
     * public Bean createInstance(Type type){
     * return new Bean();}}
     * <p>
     * 则需要通过反射获取instanceCreators，否则直接使用new HashMap<>()即可
     */
    public CollectionTypeAdapterFactory createCollectionTypeAdapterFactory(GsonBuilder gsonBuilder, boolean instanceCreators) {
        if (instanceCreators) {
            try {
                Class builder = gsonBuilder.getClass();
                Field f = builder.getDeclaredField("instanceCreators");
                f.setAccessible(true);
                @SuppressWarnings("unchecked")
                Map<Type, InstanceCreator<?>> val = (Map<Type, InstanceCreator<?>>) f.get(gsonBuilder);
                return new CollectionTypeAdapterFactory(new ConstructorConstructor(val));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                return new CollectionTypeAdapterFactory(new ConstructorConstructor(new HashMap<>()));
            }
        } else {
            return new CollectionTypeAdapterFactory(new ConstructorConstructor(new HashMap<>()));

        }
    }
}
