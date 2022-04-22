package cn.lqservice.logreq.advice;

import cn.lqservice.logreq.annotion.NoLog;
import cn.lqservice.logreq.util.AdviceRequestLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 在每个具有 @RequestBody 参数注解 或者 ResponseEntity 参数的请求前执行
 * @author Qi
 */
@Slf4j
@ControllerAdvice
public class LogRequestAdvice implements RequestBodyAdvice {

    private ThreadLocal<Long> localTimer;
    private ThreadLocal<Boolean> localFlag;
    private AdviceRequestLogger requestLogger;
    @Autowired
    public void setLocalTimer(ThreadLocal<Long> localTimer) {
        this.localTimer = localTimer;
    }
    @Autowired
    public void setLocalFlag(ThreadLocal<Boolean> localFlag) {
        this.localFlag = localFlag;
    }
    @Autowired
    public void setRequestLogger(AdviceRequestLogger requestLogger) {
        this.requestLogger = requestLogger;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        // log.info("request thread is {}", Thread.currentThread().getName());
        if (nonNoLog(methodParameter)) {
            localFlag.set(true);
            localTimer.set(System.currentTimeMillis());
        } else {
            localFlag.set(false);
        }
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        // log.info("request thread is {}", Thread.currentThread().getName());
        if (localFlag.get()) {
            requestLogger.logHeaders(inputMessage.getHeaders());
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        // log.info("request thread is {}", Thread.currentThread().getName());
        if (localFlag.get()) {
            requestLogger.logHeaders(inputMessage.getHeaders());
        }
        return body;
    }


    private boolean nonNoLog(MethodParameter methodParameter) {
        // 排除所有的 NoLog
        final NoLog noLog = methodParameter.getDeclaringClass().getAnnotation(NoLog.class);
        if (noLog == null) {
            // 查看当前方法是否具有 NoLog 注解
            final NoLog methodNoLog = methodParameter.getMethodAnnotation(NoLog.class);
            return methodNoLog == null;
        }
        return false;
    }

}
