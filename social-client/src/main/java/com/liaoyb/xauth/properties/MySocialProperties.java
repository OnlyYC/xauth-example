package com.liaoyb.xauth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "my.social")
public class MySocialProperties {
    /**
     * 社交登录，如果需要用户注册，跳转的页面
     */
    private String signUpUrl = "/socialSignUp";
    /**
     * 社交登录功能拦截的url
     */
    private String filterProcessesUrl = "/auth";
    private XAuthProperties xauth = new XAuthProperties();

    @Data
    public static class XAuthProperties {
        private String appId;
        private String appSecret;
        private String scope;
        private String providerId = "xauth";
    }

}
