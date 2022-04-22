
### LogRequest

> 自动配置 SpringBoot web项目中每个请求的日志打印  
> 测试版  
- 构建版本  
> JDK-11   
> SpringBoot 2.6.6

#### Use

1. git clone 到本地
2. mvn install 安装到本地仓库
3. 在其他 spring web 项目中引入依赖
```xml
<dependency>
    <groupId>cn.lqservice</groupId>
    <artifactId>log-request-spring-boot-starter</artifactId>
    <version>1.0</version>
</dependency>
```

#### Note
通过 SpringBoot 自动装配,默认会打印所有请求    
在 controller 类上或者指定的方法中添加 @NoLog 注解,该类中的请求或者该方法的请求日志将不会被打印
