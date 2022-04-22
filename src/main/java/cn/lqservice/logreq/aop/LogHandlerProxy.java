package cn.lqservice.logreq.aop;

import cn.lqservice.logreq.annotion.NoLog;
import cn.lqservice.logreq.util.AopRequestLogger;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Qi
 */
@Slf4j
@Aspect
public class LogHandlerProxy {

    private AopRequestLogger aopRequestLogger;
    @Autowired
    public void setAopRequestLogger(AopRequestLogger aopRequestLogger) {
        this.aopRequestLogger = aopRequestLogger;
    }

    private final ThreadLocal<Boolean> localFlag = new ThreadLocal<>();
    private final ThreadLocal<Long> localTimer = new ThreadLocal<>();

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logAllRequestMapping() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void logAllPostMapping() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void logAllGetMapping() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void logAllPutMapping() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void logAllDeleteMapping() {}

    @Pointcut("logAllRequestMapping() || logAllPostMapping() || logAllGetMapping() " +
            "|| logAllDeleteMapping() || logAllPutMapping()")
    public void logAllRequest() {}


    @Before("logAllRequest()")
    public void doLogBeforeAllRequest(JoinPoint jp) {
        if (nonNoLog(jp)) {
            localFlag.set(Boolean.TRUE);
            localTimer.set(System.currentTimeMillis());
            final Object[] args = jp.getArgs();
            aopRequestLogger.logBeforeHandler(getRequest(), args);
            return;
        }
        localFlag.set(Boolean.FALSE);
    }

    @AfterReturning(pointcut = "logAllRequest()")
    public void doLogAfterRequestHandle() {
        final Boolean nonNoLog = localFlag.get();
        if (nonNoLog != null && nonNoLog.equals(Boolean.TRUE)) {
            final Long st = localTimer.get();
            aopRequestLogger.logAfterHandler(getResponse(), st, System.currentTimeMillis());
        }
    }

    private boolean nonNoLog(JoinPoint jp) {
        final NoLog noLog = jp.getTarget().getClass().getAnnotation(NoLog.class);
        // 查看 controller 类是否具有 NoLog 注解
        if (noLog == null){
            // 查看当前方法是否具有 NoLog 注解
            final MethodSignature signature = (MethodSignature) jp.getSignature();
            return signature == null || signature.getMethod().getAnnotation(NoLog.class) == null;
        }
        return false;
    }

    private HttpServletRequest getRequest() {
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return requestAttributes.getRequest();
        }
        return null;
    }

    private HttpServletResponse getResponse() {
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return requestAttributes.getResponse();
        }
        return null;
    }
}
