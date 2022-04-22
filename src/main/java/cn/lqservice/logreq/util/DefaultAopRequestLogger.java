package cn.lqservice.logreq.util;

import cn.lqservice.logreq.util.tool.IpUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @author Qi
 */
@Slf4j
public class DefaultAopRequestLogger implements AopRequestLogger{

    @Override
    public void logBeforeHandler(HttpServletRequest request, Object[] args) {
        // 打印请求头及参数信息
        final Enumeration<String> names = request.getHeaderNames();
        StringBuilder sb = new StringBuilder();
        sb.append("\n[#INFO#] ").append("######## Request INFO ###########").append("\n");
        while (names.hasMoreElements()) {
            final String name = names.nextElement();
            sb.append("[#INFO#] ").append(formatStr(name)).append(" => ")
                    .append(request.getHeader(name))
                    .append("\n");
        }
        sb.append("[#INFO#] ").append("session").append(" :: ").append(request.getRequestedSessionId()).append("\n");
        sb.append("[#INFO#] ").append("query  ").append(" :: ").append(request.getQueryString()).append("\n");
        sb.append("[#INFO#] ").append("IP     ").append(" :: ").append(IpUtil.getRemoteIp(request)).append("\n");
        sb.append("[#INFO#] ").append("######## Request Args ############").append("\n");
        sb.append("[#INFO#] ").append("${args}").append(" :: ").append(Arrays.toString(args));
        log.info(sb.toString());
    }

    @Override
    public void logAfterHandler(HttpServletResponse response, long startTs, long endTs) {
        long diff = endTs - startTs;
        // 添加执行时间的响应头
        response.addHeader("Costs", diff + "ms");
        log.info("request handle begin at [{}] end at [{}].Total costs = [{}ms]", startTs, endTs, diff);
    }


    private String formatStr(String name) {
        int limit = 24;
        if (name.length() > limit) {
            return name.substring(0, limit);
        }
        char[] chars = new char[limit];
        for (int i = 0; i < name.length(); i++) {
            chars[i] = name.charAt(i);
        }
        for (int i = name.length(); i < limit; i++) {
            chars[i] = ' ';
        }
        return new String(chars);
    }
}
