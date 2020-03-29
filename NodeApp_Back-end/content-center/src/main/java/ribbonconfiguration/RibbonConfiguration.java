package ribbonconfiguration;

import com.banmingi.nodeapp.contentcenter.configuration.NacosWeightedRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;

/**
 * @auther 半命i 2020/3/24
 * @description
 */
//@Configuration
public class RibbonConfiguration {
    @Bean
    public IRule ribbonRule() {
        //return new RandomRule();
        return new NacosWeightedRule();
        //return new NacosSameClusterWeightedRule();
    }

}
