package com.ctd.springboot.auth.utils.auth;

import com.ctd.springboot.auth.vo.client.ClientInfoVO;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.vo.user.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Objects;

/**
 * AuthUtils
 *
 * @author chentudong
 * @date 2020/3/7 20:20
 * @since 1.0
 */
public final class AuthUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AssertUtils.class);
    public static final String TOKEN_HEADER = "Authorization";

    private AuthUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    private static final String BASIC_ = "Basic ";

    /**
     * 获取requet(head/param)中的token
     *
     * @param request request
     * @return String
     */
    public static String extractToken(HttpServletRequest request)
    {
        String token = extractHeaderToken(request);
        if (StringUtils.isBlank(token))
        {
            token = request.getParameter(OAuth2AccessToken.ACCESS_TOKEN);
            if (StringUtils.isBlank(token))
            {
                LOGGER.debug("Token not found in request parameters.  Not an OAuth2 request.");
            }
        }
        return token;
    }

    /**
     * 解析head中的token
     *
     * @param request request
     * @return String
     */
    private static String extractHeaderToken(HttpServletRequest request)
    {
        Enumeration<String> headers = request.getHeaders(TOKEN_HEADER);
        while (headers.hasMoreElements())
        {
            String value = headers.nextElement(); 
            if ((value.startsWith(OAuth2AccessToken.BEARER_TYPE)))
            {
                String authHeaderValue = value.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0)
                {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }

    /**
     * header 请求中的clientId:clientSecret
     *
     * @param request request
     * @return ClientInfoVO
     */
    public static ClientInfoVO extractClient(HttpServletRequest request)
    {
        String header = request.getHeader(TOKEN_HEADER);
        if (Objects.isNull(header) || !header.startsWith(BASIC_))
        {
            AssertUtils.msgDevelopment("请求头中client信息为空");
        }
        return extractHeaderClient(header);
    }

    /**
     * header 请求中的clientId:clientSecret
     *
     * @param header header
     * @return ClientInfoVO
     */
    public static ClientInfoVO extractHeaderClient(String header)
    {
        byte[] base64Client = header.substring(BASIC_.length()).getBytes(StandardCharsets.UTF_8);
        byte[] decoded = Base64.getDecoder().decode(base64Client);
        String clientStr = new String(decoded, StandardCharsets.UTF_8);
        String[] clientArr = clientStr.split(":");
        if (clientArr.length != 2)
        {
            AssertUtils.msgDevelopment("Invalid basic authentication token");
        }
        ClientInfoVO clientInfo = new ClientInfoVO();
        clientInfo.setClientId(clientArr[0]);
        clientInfo.setClientSecret(clientArr[1]);
        return clientInfo;
    }

    /**
     * 获取登陆的用户名
     */
    public static String getUsername(Authentication authentication)
    {
        Object principal = authentication.getPrincipal();
        String username = null;
        if (principal instanceof UserVO)
        {
            username = ((UserVO) principal).getUsername();
        } else if (principal instanceof String)
        {
            username = (String) principal;
        }
        return username;
    }
}
