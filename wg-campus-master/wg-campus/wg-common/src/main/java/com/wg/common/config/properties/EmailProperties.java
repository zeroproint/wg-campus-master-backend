package com.wg.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * email配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "email")
public class EmailProperties {
    private String host;
    private int port;
    private String user;
    private String pass;
}
