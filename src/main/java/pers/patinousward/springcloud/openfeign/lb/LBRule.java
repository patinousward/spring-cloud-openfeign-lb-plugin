package pers.patinousward.springcloud.openfeign.lb;

import feign.RequestTemplate;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @Author patinousward
 * @create 2021/4/30 下午3:18
 */
public interface LBRule<T> {

    String getRuleName();

    String getApplicationName();

    String rule(List<ServiceInstance> instances, RequestTemplate template);

    Class<T> getType();

}
