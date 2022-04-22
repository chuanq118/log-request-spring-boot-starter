package cn.lqservice.logreq.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

/**
 * @author Qi
 */
@Slf4j
public class DefaultAdviceRequestLogger implements AdviceRequestLogger {

    @Override
    public void logHeaders(HttpHeaders headers) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            sb.append("[#INFO#] ").append(entry.getKey()).append(" => ")
                    .append(listToString(entry.getValue()))
                    .append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        log.info("request headers :: \n" + sb.toString());
    }

    @Override
    public void logExecuteTime(long startTs, long endTs) {
        log.info("request start at [{}] end at [{}]. Total costs :: [{}]ms", startTs, endTs, endTs - startTs);
    }

    private String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append("; ");
        }
        sb.deleteCharAt(sb.length() - 2);
        return sb.toString().trim();
    }
}
