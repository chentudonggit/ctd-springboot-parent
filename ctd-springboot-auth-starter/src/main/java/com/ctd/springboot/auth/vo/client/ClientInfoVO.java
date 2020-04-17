package com.ctd.springboot.auth.vo.client;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * ClientInfoVO
 *
 * @author chentudong
 * @date 2020/3/14 13:20
 * @since 1.0
 */
public class ClientInfoVO implements Serializable
{
    private static final long serialVersionUID = -6757014431727661041L;

    /**
     * clientId
     */
    @JSONField(name = "client_id")
    private String clientId;

    /**
     * clientSecret
     */
    @JSONField(name = "client_secret")
    private String clientSecret;

    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public String getClientSecret()
    {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret)
    {
        this.clientSecret = clientSecret;
    }

    @Override
    public String toString()
    {
        return "ClientInfoVO{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                '}';
    }
}
