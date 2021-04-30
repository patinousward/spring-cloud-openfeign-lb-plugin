package pers.patinousward.springcloud.openfeign.lb;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Target;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author patinousward
 * @create 2021/4/30 下午3:31
 */
public class LBRequestInterceptor {

    @Bean
    public RequestInterceptor getRequestInterceptor(DiscoveryClient discoveryClient, LBRule[] lbRules) {
        Map<Class, LBRule> collect = Arrays.stream(lbRules).collect(Collectors.toMap(LBRule::getType, f -> f));
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                Target<?> target = requestTemplate.feignTarget();
                LBRule lbRule = collect.get(target.type());
                if (lbRule != null) {
                    List<ServiceInstance> instances = discoveryClient.getInstances(lbRule.getApplicationName());
                    String newTarget = lbRule.rule(instances, requestTemplate);
                    System.out.println(String.format("IRule ==> %s", newTarget));
                    requestTemplate.target(newTarget);
                }
            }
        };
    }

}
