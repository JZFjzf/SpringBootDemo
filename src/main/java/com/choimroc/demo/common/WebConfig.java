package com.choimroc.demo.common;

import com.choimroc.demo.common.gson.CollectionTypeAdapterFactory;
import com.choimroc.demo.common.gson.DoubleAdapter;
import com.choimroc.demo.common.gson.FloatAdapter;
import com.choimroc.demo.common.gson.IntegerAdapter;
import com.choimroc.demo.common.gson.LongAdapter;
import com.choimroc.demo.common.gson.StringAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.bind.TypeAdapters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author choimroc
 * @since 2019/3/8
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public Gson gson() {
        return new GsonBuilder()
//                .serializeNulls()
                .registerTypeAdapterFactory(TypeAdapters.newFactory(int.class, Integer.class, new IntegerAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(float.class, Float.class, new FloatAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(double.class, Double.class, new DoubleAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(long.class, Long.class, new LongAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(String.class, new StringAdapter()))
                .registerTypeAdapterFactory(new CollectionTypeAdapterFactory(new ConstructorConstructor(new HashMap<>())))
                .create();

//        try {
//            Class builder = gsonBulder.getClass();
//            Field f = builder.getDeclaredField("instanceCreators");
//            f.setAccessible(true);
//            Map<Type, InstanceCreator<?>> val = (Map<Type, InstanceCreator<?>>) f.get(gsonBulder);
//            gsonBulder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(new ConstructorConstructor(val)));
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setWriteAcceptCharset(false);
        stringConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
        converters.add(stringConverter);
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<>());
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(gson());
        gsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converters.add(gsonHttpMessageConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptor = registry.addInterceptor(new SecurityInterceptor());
        // 配置拦截的路径
        interceptor.addPathPatterns("/**");
        // 配置不拦截的路径
        interceptor.excludePathPatterns("**/swagger-ui.html");
    }
}
