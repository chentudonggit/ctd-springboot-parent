package com.ctd.springboot.auth.utils.jwt;

import com.alibaba.fastjson.JSONObject;
import com.ctd.springboot.common.core.constant.security.SecurityConstants;
import com.ctd.springboot.common.core.utils.rsa.RsaUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.interfaces.RSAPublicKey;
import java.util.stream.Collectors;

/**
 * JwtUtils
 *
 * @author chentudong
 * @date 2020/3/7 20:28
 * @since 1.0
 */
public final class JwtUtils {
    private static final String PUBLIC_KEY_START = "-----BEGIN PUBLIC KEY-----";
    private static final String PUBLIC_KEY_END = "-----END PUBLIC KEY-----";
    public static final String EXP = "exp";

    /**
     * 通过classpath获取公钥值
     */
    public static RSAPublicKey getRsaPublicKey() {
        Resource res = new ClassPathResource(SecurityConstants.RSA_PUBLIC_KEY);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(res.getInputStream()))) {
            String pubKey = br.lines().collect(Collectors.joining("\n"));
            pubKey = pubKey.substring(PUBLIC_KEY_START.length(), pubKey.indexOf(PUBLIC_KEY_END));
            return RsaUtils.getPublicKey(pubKey);
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * {"exp":1563256084,"user_name":"admin","authorities":["ADMIN"],"jti":"4ce02f54-3d1c-4461-8af1-73f0841a35df","client_id":"webApp","scope":["app"]}
     *
     * @param jwtToken     token值
     * @param rsaPublicKey 公钥
     * @return JSONObject
     */
    public static JSONObject decodeAndVerify(String jwtToken, RSAPublicKey rsaPublicKey) {
        SignatureVerifier rsaVerifier = new RsaVerifier(rsaPublicKey);
        Jwt jwt = JwtHelper.decodeAndVerify(jwtToken, rsaVerifier);
        return JSONObject.parseObject(jwt.getClaims());
    }

    /**
     * {"exp":1563256084,"user_name":"admin","authorities":["ADMIN"],"jti":"4ce02f54-3d1c-4461-8af1-73f0841a35df","client_id":"webApp","scope":["app"]}
     *
     * @param jwtToken token值
     * @return JSONObject
     */
    public static JSONObject decodeAndVerify(String jwtToken) {
        return decodeAndVerify(jwtToken, getRsaPublicKey());
    }

    /**
     * 判断jwt是否过期
     *
     * @param claims   jwt内容
     * @param currTime 当前时间
     * @return 未过期：true，已过期：false
     */
    public static boolean checkExp(JSONObject claims, long currTime) {
        long exp = claims.getLong(EXP);
        return exp >= currTime;
    }

    /**
     * 判断jwt是否过期
     *
     * @param claims jwt内容
     * @return 未过期：true，已过期：false
     */
    public static boolean checkExp(JSONObject claims) {
        return checkExp(claims, System.currentTimeMillis());
    }
}
