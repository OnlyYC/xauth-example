package com.liaoyb.xauth.social.xauth.api;

import lombok.Data;

/**
 * @author zhailiang
 */
@Data
public class XAuthUserInfo {
    private String openid;
    private String nickname;
    /**
     * 用户名
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
}
