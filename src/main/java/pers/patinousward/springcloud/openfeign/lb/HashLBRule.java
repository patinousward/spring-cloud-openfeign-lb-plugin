package pers.patinousward.springcloud.openfeign.lb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.Request;
import feign.RequestTemplate;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * @Author patinousward
 * @create 2021/4/30 下午3:20
 */
public class HashLBRule<T> extends AbstractLBRule<T> {

    public static final String NAME = "hash";

    private final String key;

    public HashLBRule(Class<T> type, String key) {
        super(type);
        this.key = key;
    }

    @Override
    public String getRuleName() {
        return NAME;
    }

    @Override
    public String rule(List<ServiceInstance> instances, RequestTemplate template) {
        instances.sort(Comparator.comparing(ServiceInstance::getHost));
        String value = getValue(template, key);
        int i = Math.abs(value.hashCode() % instances.size());
        ServiceInstance serviceInstance = instances.get(i);
        String host = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String path = getPath();
        if (StringUtils.isEmpty(path)) {
            return String.format("http://%s:%s", host, port);
        }
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return String.format("http://%s:%s/%s", host, port, path);
    }

    public boolean isGetRequestTemplate(RequestTemplate requestTemplate) {
        return requestTemplate.method().equalsIgnoreCase(Request.HttpMethod.GET.name());
    }

    public String getValue(RequestTemplate requestTemplate, String key) {
        if (isGetRequestTemplate(requestTemplate)) {
            Collection<String> strings = requestTemplate.queries().get(key);
            if (!CollectionUtils.isEmpty(strings)) {
                return strings.iterator().next();
            }
        } else {
            byte[] body = requestTemplate.body();
            JSONObject jsonObject = JSON.parseObject(new String(body));
            return jsonObject.getString(key);
        }
        return null;
    }

}
