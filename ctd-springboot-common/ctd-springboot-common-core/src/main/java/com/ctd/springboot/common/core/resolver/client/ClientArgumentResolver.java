package com.ctd.mall.framework.common.core.resolver.client;

import com.ctd.mall.framework.common.core.constant.security.SecurityConstants;
import com.ctd.mall.framework.common.core.annotation.login.LoginClient;
import com.ctd.mall.framework.common.core.exception.UnifiedException;
import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * head中的应用参数注入clientId
 *
 * @author chentudong
 * @date 2020/3/26 1:02
 * @since 1.0
 */
public class ClientArgumentResolver implements HandlerMethodArgumentResolver
{
    /**
     * 入参筛选
     *
     * @param methodParameter 参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter)
    {
        return methodParameter.hasParameterAnnotation(LoginClient.class) && methodParameter.getParameterType().equals(String.class);
    }

    /**
     * @param methodParameter       入参集合
     * @param modelAndViewContainer model 和 view
     * @param nativeWebRequest      web相关
     * @param webDataBinderFactory  入参解析
     * @return 包装对象
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory)
    {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        if (Objects.nonNull(request))
        {
            String clientId = request.getHeader(SecurityConstants.TENANT_HEADER);
            AssertUtils.isNullToUser(clientId, "服务器异常，请联系管理员。");
            return clientId;
        }
        throw new UnifiedException("服务器异常，请联系管理员。");
    }
}
