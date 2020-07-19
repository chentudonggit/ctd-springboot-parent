package com.ctd.springboot.auth.converter.authentication;

import com.ctd.springboot.common.core.exception.InternalException;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.utils.param.ParamUtils;
import com.ctd.springboot.common.core.vo.user.UserVO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * jwt返回的principal改为返回UserVO
 *
 * @author chentudong
 * @date 2020/3/7 16:19
 * @see org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter
 * @since 1.0
 */
public class CustomUserAuthenticationConverter implements UserAuthenticationConverter {
    /**
     * defaultAuthorities
     */
    private Collection<? extends GrantedAuthority> defaultAuthorities;

    /**
     * UserDetailsService
     */
    private UserDetailsService userDetailsService;

    /**
     * Optional {@link UserDetailsService} to use when extracting
     * an {@link Authentication} from the incoming map.
     *
     * @param userDetailsService the userDetailsService to set
     */
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Default value for authorities if an Authentication is being created and the input has no data for authorities.
     * Note that unless this property is set, the default Authentication created by {@link #extractAuthentication(Map)}
     * will be unauthenticated.
     *
     * @param defaultAuthorities the defaultAuthorities to set. Default null.
     */
    public void setDefaultAuthorities(String[] defaultAuthorities) {
        this.defaultAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                .arrayToCommaDelimitedString(defaultAuthorities));
    }

    /**
     * 用户名，用户权限
     *
     * @param authentication authentication
     * @return Map<String, ?>
     */
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(USERNAME, authentication.getName());
        //权限是否为空
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (AssertUtils.nonNull(authorities)) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }

    /**
     * jwt返回的principal改为返回UserVO
     *
     * @param map map
     * @return Authentication
     */
    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (!AssertUtils.isNull(map)) {
            if (map.containsKey(USERNAME)) {
                Object principal = map.get(USERNAME);
                Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
                if (Objects.nonNull(userDetailsService)) {
                    UserDetails user = userDetailsService.loadUserByUsername((String) map.get(USERNAME));
                    authorities = user.getAuthorities();
                    principal = user;
                } else {
                    UserVO user = new UserVO();
                    user.setUsername((String) principal);
                    user.setId(ParamUtils.getParam(map, "id"));
                    principal = user;
                }
                return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
            }
        }
        return null;
    }

    /**
     * 获取权限
     *
     * @param map map
     * @return Collection
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (AssertUtils.isNull(map)) {
            return new ArrayList<>(0);
        }
        //默认
        if (!map.containsKey(AUTHORITIES)) {
            return defaultAuthorities;
        }
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }

        throw new InternalException("Authorities 请使用 String 或 Collection 封装  Authorities must be either " +
                "a String or a Collection");
    }
}