package com.ctd.springboot.common.core.constant.tencent;

/**
 * 腾讯常量
 *
 * @author chentudong
 * @date 2020/4/4 22:23
 * @since 1.0
 */
public interface TencentConstants {
    /**
     * 微信地址
     */
    String WEI_XIN_CONNECT_QR_CONNECT = "https://open.weixin.qq.com/connect/qrconnect";

    /**
     * 微信登录二维码
     */
    String WEI_XIN_LOGIN_QR_CODE_CONNECT_QR_CONNECT = "https://open.weixin.qq.com/connect/qrconnect" +
            "?appid=%s" +
            "&redirect_uri=%s" +
            "&response_type=code" +
            "&scope=snsapi_login" +
            "&state=%s" +
            "#wechat_redirect";

}
