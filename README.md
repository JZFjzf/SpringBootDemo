## 一、新项目使用
1、新项目需要修改的地方

- 第三级包名`demo`改为自己项目对应的包名

- `Application.java`：`@MapperScan` 中的路径改为自己项目中对应的包名

2、配置`gradle`打包为war(如有需要才进行以下配置)

- 在`Application.java`同级目录下新增一个继承自`SpringBootServletInitializer`的类，如

```java
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
```

- 在`build.gradle`的`plugins`节点加入` id 'war'`,如下

```groovy
plugins {
    id 'org.springframework.boot' version '2.2.0.RELEASE'
    id 'io.spring.dependency-management' version '1.0.8.RELEASE'
    id 'java'
    id 'war'
}
```

- 在`dependencies`节点中加入依赖，如下
```groovy
dependencies {
     providedRuntime 'org.springframework.boot:spring-boot-starter-tomcat'
}
```
> PS:使用war包部署时，建议将`version`置空

## 二、使用说明
1、`com.choimroc.demo.tool`包说明

2、权限验证
- 验证文件为`com.choimroc.demo.security.SecurityInterceptor`,需要自行修改为自己项目的验证逻辑
。使用`@IgnoreSecurity`注解过滤掉不需要进行权限验证的接口
- 


