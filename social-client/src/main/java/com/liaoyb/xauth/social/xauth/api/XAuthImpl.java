package com.liaoyb.xauth.social.xauth.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liaoyb.xauth.social.xauth.dto.CheckTokenResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;
import java.util.Base64;

@Slf4j
public class XAuthImpl extends AbstractOAuth2ApiBinding implements XAuth {

    private static final String URL_CHECK_TOKEN = "http://localhost:8080/oauth/check_token?token=%s";
    private static final String URL_GET_USERINFO = "http://localhost:8080/api/sys/account/user-info?token=%s";

    private String accessToken;
    private final String appId;
    private final String appSecret;

    private ObjectMapper objectMapper = new ObjectMapper();

    public XAuthImpl(String appId, String appSecret, String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.accessToken = accessToken;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    @Override
    public XAuthUserInfo getUserInfo() {
        //获取用户详细信息
        String url = String.format(URL_GET_USERINFO, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        log.debug("xauth用户信息:" + result);

        XAuthUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, XAuthUserInfo.class);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }

}
