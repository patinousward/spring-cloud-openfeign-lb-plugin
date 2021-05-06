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

    /**
     *
     * @param instances 当前注册中心中的实例
     * @param template 当此请求template，可以获取到请求参数
     * @return 返回负载均衡算法的请求url
     */
    String rule(List<ServiceInstance> instances, RequestTemplate template);

    Class<T> getType();

}
