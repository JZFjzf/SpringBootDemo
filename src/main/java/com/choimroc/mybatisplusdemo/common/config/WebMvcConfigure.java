package com.choimroc.mybatisplusdemo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfigure extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //其中OTA表示访问的前缀。"file:D:/OTA/"是文件真实的存储路径
        registry.addResourceHandler("/face/register/**").addResourceLocations("file:"+UrlUtils.baseurl +"FaceAttendance/img/");
        registry.addResourceHandler("/face/attendance/**").addResourceLocations("file:"+UrlUtils.baseurl +"FaceAttendance/discern_img/");
    }
}
