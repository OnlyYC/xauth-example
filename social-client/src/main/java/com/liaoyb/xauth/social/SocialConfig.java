/**
 *
 */
package com.liaoyb.xauth.social;

import com.liaoyb.xauth.config.MySocialUser;
import com.liaoyb.xauth.properties.MySocialProperties;
import com.liaoyb.xauth.social.view.ConnectView;
import com.liaoyb.xauth.social.xauth.connect.XAuthConnectionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.connect.web.DisconnectInterceptor;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.servlet.View;

import javax.sql.DataSource;
import java.util.List;

/**
 * 社交登录配置主类
 *
 * @author liaoyanbo
 */
@Configuration
@EnableSocial
@ConditionalOnProperty(prefix = "my.social.xauth", name = "app-id")
@Import(ConnectController.class)
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MySocialProperties mySocialProperties;

    private final List<ConnectInterceptor<?>> connectInterceptors;

    private final List<DisconnectInterceptor<?>> disconnectInterceptors;


    public SocialConfig(
            ObjectProvider<List<ConnectInterceptor<?>>> connectInterceptorsProvider,
            ObjectProvider<List<DisconnectInterceptor<?>>> disconnectInterceptorsProvider) {
        this.connectInterceptors = connectInterceptorsProvider.getIfAvailable();
        this.disconnectInterceptors = disconnectInterceptorsProvider.getIfAvailable();
    }


    /**
     * 注册的实现(第三方用户没有绑定过账号时，系统会自动登录，登录授权后的操作)
     */
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Override
    public UserIdSource getUserIdSource() {
        return new UserIdSource() {
            @Override
            public String getUserId() {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal instanceof MySocialUser) {
                    MySocialUser mySocialUser = (MySocialUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    return mySocialUser.getUserId();
                } else if (principal instanceof String) {
                    String mySocialUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    return mySocialUser;
                }
                return null;
            }
        };
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
                                       Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }

    protected ConnectionFactory<?> createConnectionFactory() {
        MySocialProperties.XAuthProperties xAuthProperties = mySocialProperties.getXauth();
        return new XAuthConnectionFactory(xAuthProperties.getProviderId(), xAuthProperties.getAppId(), xAuthProperties.getAppSecret());
    }


    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
        repository.setTablePrefix("xauth_");
        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * 社交登录配置类，供浏览器或app模块引入设计登录配置用。
     *
     * @return
     */
    @Bean
    public SpringSocialConfigurer socialSecurityConfig() {
        String filterProcessesUrl = mySocialProperties.getFilterProcessesUrl();
        //可以继承SpringSocialConfigurer,实现自定义的后处理逻辑
        SpringSocialConfigurer configurer = new SpringSocialConfigurer();
        configurer.signupUrl(mySocialProperties.getSignUpUrl());
        return configurer;
    }

    /**
     * 用来处理注册流程的工具类
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator)) {
        };
    }


    /**
     * 自定义绑定成功视图
     *
     * @return
     */
    @Bean({"connect/xauthConnect", "connect/xauthConnected"})
    @ConditionalOnMissingBean(name = "xauthConnectedView")
    public View xauthConnectedView() {
        return new ConnectView();
    }
}
