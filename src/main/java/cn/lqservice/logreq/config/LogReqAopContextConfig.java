package cn.lqservice.logreq.config;

import cn.lqservice.logreq.aop.LogHandlerProxy;
import cn.lqservice.logreq.util.AopRequestLogger;
import cn.lqservice.logreq.util.DefaultAopRequestLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Qi
 */
@Configuration
public class LogReqAopContextConfig {

    @Bean
    public LogHandlerProxy logHandlerProxy() {
        return new LogHandlerProxy();
    }

    @Bean
    public AopRequestLogger aopRequestLogger() {
        return new DefaultAopRequestLogger();
    }
}
