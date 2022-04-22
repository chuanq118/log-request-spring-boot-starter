package cn.lqservice.logreq.util;

import org.springframework.http.HttpHeaders;

/**
 * @author Qi
 */
public interface AdviceRequestLogger {

    /**
     * 打印请求头
     * @param headers Spring Web http 模块请求头对象
     */
    void logHeaders(HttpHeaders headers);

    /**
     * 打印请求的执行时间
     * @param startTs 开始时间戳(毫秒)
     * @param endTs 结束时间戳(毫秒)
     */
    void logExecuteTime(long startTs, long endTs);
}
