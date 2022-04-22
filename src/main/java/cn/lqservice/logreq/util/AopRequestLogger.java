package cn.lqservice.logreq.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Qi
 */
public interface AopRequestLogger {

    /**
     * 在请求被处理前进行日志打印
     * @param request 当前处理线程的请求对象 / request can be null!
     * @param args 请求参数
     */
    void logBeforeHandler(HttpServletRequest request, Object[] args);

    /**
     * 在请求完成处理后进行日志打印
     * @param response 响应对象
     * @param startTs 请求开始处理的时间戳
     * @param endTs 请求接受处理的时间戳
     */
    void logAfterHandler(HttpServletResponse response, long startTs, long endTs);
}
