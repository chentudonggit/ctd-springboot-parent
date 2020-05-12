package com.ctd.springboot.auth.permission;

import cn.hutool.core.collection.CollectionUtil;
import com.ctd.springboot.auth.properties.security.SecurityProperties;
import com.ctd.springboot.auth.utils.auth.AuthUtils;
import com.ctd.springboot.common.core.constant.CommonConstant;
import com.ctd.springboot.common.core.enums.method.MethodEnum;
import com.ctd.springboot.common.core.holder.context.TenantContextHolder;
import com.ctd.springboot.common.core.vo.menu.MenuVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * AbstractDefaultPermission
 *
 * @author chentudong
 * @date 2020/5/12 11:15 上午
 * @since 1.0
 */
public abstract class AbstractDefaultPermission
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDefaultPermission.class);
    @Autowired
    private SecurityProperties securityProperties;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 查询当前用户拥有的资源权限
     *
     * @param roleCodes roleCodes
     * @return List<MenuVO>
     */
    public abstract List<MenuVO> findMenuByRoleCodes(Collection<String> roleCodes);

    /**
     * 校验用户权限
     *
     * @param authentication authentication
     * @param requestMethod  requestMethod
     * @param requestUri     requestUri
     * @return boolean
     */
    public boolean hasPermission(Authentication authentication, String requestMethod, String requestUri)
    {
        // 前端跨域OPTIONS请求预检放行 也可通过前端配置代理实现
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(requestMethod))
        {
            return true;
        }
        if (!(authentication instanceof AnonymousAuthenticationToken))
        {
            //判断是否开启url权限验证
            if (!securityProperties.getAuth().getUrlPermission().getEnable())
            {
                return true;
            }
            //超级管理员admin不需认证
            String username = AuthUtils.getUsername(authentication);
            if (CommonConstant.ADMIN_USER_NAME.equals(username))
            {
                return true;
            }

            OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
            //判断应用黑白名单
            if (!isNeedAuth(auth2Authentication.getOAuth2Request().getClientId()))
            {
                return true;
            }

            //判断不进行url权限认证的api，所有已登录用户都能访问的url
            for (String path : securityProperties.getAuth().getUrlPermission().getIgnoreUrls())
            {
                if (antPathMatcher.match(path, requestUri))
                {
                    return true;
                }
            }

            List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
            if (CollectionUtil.isEmpty(grantedAuthorityList))
            {
                LOGGER.warn("角色列表为空：{}", authentication.getPrincipal());
                return false;
            }

            //保存租户信息
            String clientId = auth2Authentication.getOAuth2Request().getClientId();
            TenantContextHolder.setTenant(clientId);

            Collection<String> roleCodes = grantedAuthorityList.stream().map(SimpleGrantedAuthority :: getAuthority).collect(Collectors.toSet());
            List<MenuVO> menuList = findMenuByRoleCodes(roleCodes);
            for (MenuVO menu : menuList)
            {
                if (StringUtils.isNotBlank(menu.getUrl()) && antPathMatcher.match(menu.getUrl(), requestUri))
                {
                    MethodEnum method = menu.getMethod();
                    if (Objects.nonNull(method))
                    {
                        return requestMethod.equalsIgnoreCase(method.toString());
                    }
                }
            }
        }

        return false;
    }

    /**
     * 判断应用是否满足白名单和黑名单的过滤逻辑
     *
     * @param clientId 应用id
     * @return true(需要认证)，false(不需要认证)
     */
    private boolean isNeedAuth(String clientId)
    {
        boolean result = true;
        //白名单
        List<String> includeClientIds = securityProperties.getAuth().getUrlPermission().getIncludeClientIds();
        //黑名单
        List<String> exclusiveClientIds = securityProperties.getAuth().getUrlPermission().getExclusiveClientIds();

        if (Objects.nonNull(includeClientIds) && (!includeClientIds.isEmpty()))
        {
            result = includeClientIds.contains(clientId);
        } else if (Objects.nonNull(exclusiveClientIds) && (!exclusiveClientIds.isEmpty()))
        {
            result = !exclusiveClientIds.contains(clientId);
        }
        return result;
    }
}
