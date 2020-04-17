package com.ctd.springboot.auth.serializer.oauth;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.*;

/**
 * OAuth2AuthenticationSerializer
 *
 * @author chentudong
 * @date 2020/3/27 17:35
 * @since 1.0
 */
public class OAuth2AuthenticationSerializer implements ObjectDeserializer
{
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName)
    {
        if (type == OAuth2Authentication.class)
        {
            try
            {
                Object o = parse(parser);
                if (Objects.isNull(o))
                {
                    return null;
                } else if (o instanceof OAuth2Authentication)
                {
                    return (T) o;
                }

                JSONObject jsonObject = (JSONObject) o;
                OAuth2Request request = parseOauthRequest(jsonObject);
                AbstractAuthenticationToken userAuthentication = jsonObject.getObject("userAuthentication", AbstractAuthenticationToken.class);
                return (T) new OAuth2Authentication(request, userAuthentication);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    /**
     * 解析json 转成  OAuth2Request
     *
     * @param jsonObject jsonObject
     * @return OAuth2Request
     */
    private OAuth2Request parseOauthRequest(JSONObject jsonObject)
    {
        if (AssertUtils.isNull(jsonObject))
        {
            return null;
        }
        JSONObject json = jsonObject.getObject("oAuth2Request", JSONObject.class);
        Map<String, String> requestParameters = getStringMap(json, "requestParameters");
        String clientId = json.getString("clientId");
        String grantType = json.getString("grantType");
        String redirectUri = json.getString("redirectUri");
        Boolean approved = json.getBoolean("approved");
        Set<String> responseTypes = getSet(json, "responseTypes");
        Set<String> scope = getSet(json, "scope");
        Set<GrantedAuthority> grantedAuthorities = grantedAuthorities(json, "authorities");
        Set<String> resourceIds = getSet(json, "resourceIds");
        Map<String, Serializable> extensions = getMap(json, "extensions");
        OAuth2Request request = new OAuth2Request(requestParameters, clientId, grantedAuthorities, approved,
                scope, resourceIds, redirectUri, responseTypes, extensions);
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientId, scope, grantType);
        request.refresh(tokenRequest);
        return request;
    }

    /**
     * grantedAuthorities
     *
     * @param json json
     * @param key  key
     * @return Set<GrantedAuthority>
     */
    public Set<GrantedAuthority> grantedAuthorities(JSONObject json, String key)
    {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<String> authorities = getSet(json, key);
        if (AssertUtils.nonNull(authorities))
        {
            authorities.forEach(s -> grantedAuthorities.add(new SimpleGrantedAuthority(s)));
        }
        return grantedAuthorities;
    }

    /**
     * getMap
     *
     * @param json json
     * @param key  key
     * @return Map<String, Serializable>
     */
    public Map<String, Serializable> getMap(JSONObject json, String key)
    {
        if (Objects.nonNull(json) && StringUtils.isNotBlank(key))
        {
            json.getObject(key,
                    new TypeReference<HashMap<String, Serializable>>()
                    {
                    });
        }
        return new HashMap<>(0);
    }

    /**
     * 获取map
     *
     * @param json json
     * @param key  key
     * @return Map<String, String>
     */
    public Map<String, String> getStringMap(JSONObject json, String key)
    {
        if (Objects.nonNull(json) && StringUtils.isNotBlank(key))
        {
            return json.getObject(key, Map.class);
        }
        return new HashMap<>(0);
    }

    public Set<String> getSet(JSONObject json, String key)
    {
        if (Objects.nonNull(json) && StringUtils.isNotBlank(key))
        {
            return json.getObject(key, new TypeReference<HashSet<String>>()
            {
            });
        }
        return new HashSet<>(0);
    }


    @Override
    public int getFastMatchToken()
    {
        return 0;
    }

    private Object parse(DefaultJSONParser parse)
    {
        JSONObject object = new JSONObject(parse.lexer.isEnabled(Feature.OrderedField));
        Object parsedObject = parse.parseObject(object);
        if (parsedObject instanceof JSONObject)
        {
            return parsedObject;
        } else if (parsedObject instanceof OAuth2Authentication)
        {
            return parsedObject;
        } else
        {
            return Objects.isNull(parsedObject) ? null : new JSONObject((Map<String, Object>) parsedObject);
        }
    }
}
