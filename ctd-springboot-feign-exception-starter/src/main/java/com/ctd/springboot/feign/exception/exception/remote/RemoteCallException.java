package com.ctd.springboot.feign.exception.exception.remote;

import com.ctd.springboot.common.core.domain.exception.ExceptionChain;
import com.ctd.springboot.feign.exception.context.FeignExceptionHandlerContext;
import com.ctd.springboot.feign.exception.exception.remote.base.BaseRemoteCallException;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * RemoteCallException
 *
 * @author chentudong
 * @date 2020/8/11 21:48
 * @since 1.0
 */
public class RemoteCallException extends BaseRemoteCallException {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteCallException.class);
    private final List<StackTraceElement> stackTraceElements = new ArrayList<>(2);

    private boolean isAddThis = false;

    private List<ExceptionChain> exceptionChains;

    public RemoteCallException() {
    }

    public RemoteCallException(String message) {
        super(message);
    }

    public RemoteCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteCallException(String message, @NonNull List<ExceptionChain> exceptionChains) {
        super(message);
        this.exceptionChains = exceptionChains;
        for (int i = 0; i < exceptionChains.size(); i++) {
            String status = i == 0 ? "HAPPEN" : "THROW";
            this.create(exceptionChains.get(i), status);
        }
    }

    public ExceptionChain getRawExceptionInfo() {
        return CollectionUtils.isEmpty(exceptionChains) ? null : exceptionChains.get(0);
    }


    public List<ExceptionChain> getExceptionChains() {
        return exceptionChains;
    }

    public void setExceptionChains(List<ExceptionChain> exceptionChains) {
        this.exceptionChains = exceptionChains;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        if (stackTraceElements.isEmpty()) {
            return super.getStackTrace();
        }
        return stackTraceElements.toArray(new StackTraceElement[0]);
    }

    /**
     * throwException
     *
     * @param message message
     * @return BaseRemoteCallException
     */
    @Override
    public BaseRemoteCallException throwException(String message) {
        return new RemoteCallException(message);
    }

    /**
     * throwException
     *
     * @param message         message
     * @param exceptionChains exceptionChains
     * @return BaseRemoteCallException
     */
    @Override
    public BaseRemoteCallException throwException(String message, List<ExceptionChain> exceptionChains) {
        if (Objects.isNull(exceptionChains)) {
            return new RemoteCallException(message);
        }
        return new RemoteCallException(message, exceptionChains);
    }

    /**
     * throwException
     *
     * @param message message
     * @param cause   cause
     * @return BaseRemoteCallException
     */
    @Override
    public BaseRemoteCallException throwException(String message, Throwable cause) {
        return new RemoteCallException(message, cause);
    }

    /**
     * 判断异常是否为原始异常的子类
     *
     * @param exception exception
     * @return Boolean
     */
    @Override
    public Boolean isInstanceOf(Class<? extends Throwable> exception) {
        ExceptionChain rawExceptionInfo = this.getRawExceptionInfo();
        return Objects.nonNull(rawExceptionInfo) && rawExceptionInfo.isAssignableFrom(exception);
    }

    @Override
    public void printStackTrace() {
        if (!isAddThis) {
            this.addThis();
            isAddThis = true;
        }
        LOGGER.error("RemoteCallException : {}" + this.getMessage());
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            LOGGER.error("\t {}", stackTraceElement);
        }
    }

    private void addThis() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String requestPath = "";
        if (requestAttributes instanceof ServletRequestAttributes) {
            requestPath = ((ServletRequestAttributes) requestAttributes).getRequest().getRequestURI();
        }
        ExceptionChain exceptionChain = new ExceptionChain();
        exceptionChain.setApplicationName(FeignExceptionHandlerContext.getApplicationName());
        exceptionChain.setPath(requestPath);
        exceptionChain.setTimestamp(new Date());
        exceptionChain.setExceptionClass(LOGGER.getName());
        exceptionChain.setMessage(this.getMessage());
        this.create(exceptionChain, "END");
    }

    private void create(ExceptionChain exceptionChain, String status) {
        String format = "[%s]:[`http://%s%s`] timestamp:'%s',message:'%s',exception:'%s',path: '%s'";
        String str = String.format(format,
                status,
                exceptionChain.getApplicationName(),
                exceptionChain.getPath(),
                DateFormatUtils.format(exceptionChain.getTimestamp(), DATE_FORMAT),
                exceptionChain.getMessage(),
                exceptionChain.getExceptionClass(),
                exceptionChain.getPath()
        );
        StackTraceElement stackTraceElement = new StackTraceElement(
                str, "", "", 0
        );
        this.stackTraceElements.add(stackTraceElement);
    }
}
