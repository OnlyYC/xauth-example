package com.liaoyb.xauth.social.xauth.connect;

import com.liaoyb.xauth.social.xauth.api.XAuth;
import com.liaoyb.xauth.social.xauth.api.XAuthImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author zhailiang
 */
public class XAuthServiceProvider extends AbstractOAuth2ServiceProvider<XAuth> {

    private static final String URL_AUTHORIZE = "http://localhost:8080/oauth/authorize";

    private static final String URL_ACCESS_TOKEN = "http://localhost:8080/oauth/token";
    private final String appId;
    private final String appSecret;

    public XAuthServiceProvider(String appId, String appSecret) {
        super(new OAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
        this.appSecret = appSecret;
    }

    @Override
    public XAuth getApi(String accessToken) {
        return new XAuthImpl(appId, appSecret, accessToken);
    }

}
