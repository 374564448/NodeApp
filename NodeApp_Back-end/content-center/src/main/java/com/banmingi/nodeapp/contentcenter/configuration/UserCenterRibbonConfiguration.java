package com.banmingi.nodeapp.contentcenter.configuration;

        import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;
import ribbonconfiguration.RibbonConfiguration;

/**
 * @auther 半命i 2020/3/24
 * @description
 */
@Configuration
@RibbonClient(name = "user-center",configuration = RibbonConfiguration.class)
//@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class UserCenterRibbonConfiguration {
}
