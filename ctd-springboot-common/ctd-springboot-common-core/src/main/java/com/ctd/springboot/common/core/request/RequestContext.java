package com.ctd.springboot.common.core.request;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * RequestContext
 *
 * @author chentudong
 * @date 2020/7/28 19:56
 * @since 1.0
 */
public class RequestContext {
    private static List<String> IP_TYPES = new ArrayList<>(6);
    public static final String UNKNOWN = "unknown";

    static {
        IP_TYPES.add("HTTP_CLIENT_IP");
        IP_TYPES.add("x-forwarded-for");
        IP_TYPES.add("Proxy-Client-IP");
        IP_TYPES.add("WL-Proxy-Client-IP");
        IP_TYPES.add("HTTP_X_FORWARDED_FOR");
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    public static String getRemoteAddr() {
        return getRequest().getRemoteAddr();
    }

    public static String getIpAddress() {
        HttpServletRequest request = getRequest();
        String ip = null;
        for (String ipType : IP_TYPES) {
            ip = request.getHeader(ipType);
            if (StringUtils.isNotBlank(ip) && (!UNKNOWN.equalsIgnoreCase(ip))) {
                break;
            }
        }
        if (StringUtils.isBlank(ip) || (UNKNOWN.equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    public static String getURL() {
        return getRequest().getRequestURL().toString();
    }

    public static String getMethod() {
        return getRequest().getMethod();
    }
}
