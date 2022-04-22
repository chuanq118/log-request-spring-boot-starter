package cn.lqservice.logreq.config;

import cn.lqservice.logreq.advice.LogRequestAdvice;
import cn.lqservice.logreq.advice.LogResponseAdvice;
import cn.lqservice.logreq.util.DefaultAdviceRequestLogger;
import cn.lqservice.logreq.util.AdviceRequestLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Qi
 */
@Configuration
public class LogReqAdviceContextConfig {


    @Bean
    public ThreadLocal<Long> localTimer() {
        return new ThreadLocal<Long>();
    }

    @Bean
    public ThreadLocal<Boolean> localFlag() {
        return new ThreadLocal<Boolean>();
    }

    @Bean
    public AdviceRequestLogger requestLogger() {
        return new DefaultAdviceRequestLogger();
    }

    @Bean
    public LogRequestAdvice logRequestAdvice() {
        return new LogRequestAdvice();
    }

    @Bean
    public LogResponseAdvice logResponseAdvice() {
        return new LogResponseAdvice();
    }
}
