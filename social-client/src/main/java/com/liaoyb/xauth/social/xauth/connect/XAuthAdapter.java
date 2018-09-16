package com.liaoyb.xauth.social.xauth.connect;

import com.liaoyb.xauth.social.xauth.api.XAuth;
import com.liaoyb.xauth.social.xauth.api.XAuthUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author zhailiang
 */
public class XAuthAdapter implements ApiAdapter<XAuth> {

    @Override
    public boolean test(XAuth api) {
        return true;
    }

    @Override
    public void setConnectionValues(XAuth api, ConnectionValues values) {
        XAuthUserInfo userInfo = api.getUserInfo();
        values.setDisplayName(userInfo.getNickname());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenid());
    }

    @Override
    public UserProfile fetchUserProfile(XAuth api) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateStatus(XAuth api, String message) {
        //do noting
    }

}
