package com.liaoyb.xauth.config;

import com.liaoyb.xauth.properties.MySocialProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author liaoyanbo
 */
@Configuration
@EnableConfigurationProperties(MySocialProperties.class)
public class SecurityPropertiesConfig {


}
