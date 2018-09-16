package com.liaoyb.xauth.social.xauth.connect;

import com.liaoyb.xauth.social.xauth.api.XAuth;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author zhailiang
 */
public class XAuthConnectionFactory extends OAuth2ConnectionFactory<XAuth> {

    public XAuthConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new XAuthServiceProvider(appId, appSecret), new XAuthAdapter());
    }

}
