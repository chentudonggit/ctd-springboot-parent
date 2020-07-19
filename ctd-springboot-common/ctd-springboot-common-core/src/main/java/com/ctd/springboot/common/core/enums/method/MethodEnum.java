package com.ctd.springboot.common.core.enums.method;

import org.apache.commons.lang3.StringUtils;

/**
 * MethodEnum
 *
 * @author chentudong
 * @date 2020/3/8 23:02
 * @since 1.0
 */
public enum MethodEnum {
    /**
     *
     */
    GET,

    /**
     * Post
     */
    POST,

    /**
     * Put
     */
    PUT,

    /**
     * Delete
     */
    DELETE;

    public static MethodEnum value(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        switch (key) {
            case "Get":
            case "GET":
            case "get":
                return GET;
            case "Post":
            case "POST":
            case "post":
                return POST;
            case "Put":
            case "PUT":
            case "put":
                return PUT;
            case "Delete":
            case "DELETE":
            case "delete":
                return DELETE;
            default:
                return null;
        }
    }
}
