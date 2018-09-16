package com.liaoyb.xauth.web.controller;

import com.liaoyb.xauth.web.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * @param user
     * @param request
     */
    @PostMapping("/regist")
    public void regist(UserForm user, HttpServletRequest request) {
        //todo 注册用户（保存到数据库，并返回用户id）
        String userId = user.getUsername();

        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        //绑定关系(userId 和第三方登录的用户id)
        providerSignInUtils.doPostSignUp(userId, servletWebRequest);


        //todo 注册后登录(转发到自定义登录接口)

    }
}
