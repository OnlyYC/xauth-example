package com.liaoyb.xauth.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {
    @Autowired
    private ProviderSignInUtils providerSignInUtils;


    @RequestMapping("/test")
    public String test(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", userDetails.getUsername());
        return "test";
    }

    @RequestMapping("socialSignUp")
    public String socialSignUp(Model model, HttpServletRequest request) {
        //获取社交账号信息
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        Connection connection = providerSignInUtils.getConnectionFromSession(servletWebRequest);
        model.addAttribute("nickname", connection.getDisplayName());
        model.addAttribute("provide", "XAuth");
        return "social-sign-up";
    }
}
