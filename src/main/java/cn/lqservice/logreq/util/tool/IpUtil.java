package cn.lqservice.logreq.util.tool;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Qi
 */
public class IpUtil {

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    public static String getRemoteIp(HttpServletRequest request) {
        String ip = "0.0.0.0";
        for (String header: IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                ip = ipList.split(",")[0];
            }
            if ("0:0:0:0:0:0:0:1".equals(ip)) ip = "127.0.0.1";
        }
        return ip;
    }
}
