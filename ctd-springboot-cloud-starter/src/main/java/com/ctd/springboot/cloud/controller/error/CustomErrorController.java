package com.ctd.springboot.cloud.controller.error;

import com.ctd.springboot.common.core.common.exception.CommonException;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * CustomErrorController
 *
 * @author LYJ
 * @date 2020/8/13 20:08
 * @since 1.0
 */
@RestController
public class CustomErrorController extends BasicErrorController {
    @Resource
    private ErrorAttributes errorAttributes;
    @Resource
    private ErrorProperties errorProperties;

    public CustomErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }


    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Throwable throwable = this.errorAttributes.getError(new ServletWebRequest(request));
        Map<String, Object> body = new HashMap<>(5);
        ResponseEntity<Map<String, Object>> entity = new ResponseEntity<>(body, this.getStatus(request));
        CommonException.exception(throwable, body);
        body.put("error", this.getErrorAttributes(request, this.isIncludeStackTrace(request, MediaType.ALL)));
        return entity;
    }

    private Map<String, Object> errorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        WebRequest webRequest = new ServletWebRequest(request);
        return this.errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
    }

    private boolean includeStackTrace(HttpServletRequest request, MediaType produces) {
        ErrorProperties.IncludeStacktrace include = this.errorProperties.getIncludeStacktrace();
        if (ErrorProperties.IncludeStacktrace.ALWAYS.equals(include)) {
            return true;
        } else {
            return ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM.equals(include) ? this.traceParameter(request) : false;
        }
    }

    private HttpStatus status(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (Objects.isNull(statusCode)) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }

    private boolean traceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (Objects.isNull(parameter)) {
            return false;
        } else {
            return !"false".equalsIgnoreCase(parameter);
        }
    }

}
