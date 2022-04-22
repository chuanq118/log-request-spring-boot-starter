package cn.lqservice.logreq.advice;

import cn.lqservice.logreq.util.AdviceRequestLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 在每个 @ResponseBody 或者 ResponseEntity 的请求方法返回前执行
 * @author Qi
 */
@Slf4j
@ControllerAdvice
public class LogResponseAdvice implements ResponseBodyAdvice<Object> {

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
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // log.info("response thread is {}", Thread.currentThread().getName());
        final Boolean isEnable = localFlag.get();
        if (isEnable != null && isEnable) {
            requestLogger.logExecuteTime(localTimer.get(), System.currentTimeMillis());
        }
        return body;
    }
}
