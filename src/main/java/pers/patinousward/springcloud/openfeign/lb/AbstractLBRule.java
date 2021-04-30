package pers.patinousward.springcloud.openfeign.lb;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.StringUtils;

/**
 * @Author patinousward
 * @create 2021/4/30 下午3:55
 */
public abstract class AbstractLBRule<T> implements LBRule<T> {

    private final Class<T> type;
    private final String applicationName;
    private final String path;

    public AbstractLBRule(Class<T> type) {
        this.type = type;
        FeignClient annotation = type.getAnnotation(FeignClient.class);
        if (annotation == null) {
            throw new CustomerOpenFeignLBException(1001, "can not find FeignClient annotation");
        }
        if (StringUtils.isEmpty(annotation.value()) && StringUtils.isEmpty(annotation.name())) {
            throw new CustomerOpenFeignLBException(1002, "value/name in FeignClient annotation can not be empty");
        }
        if (StringUtils.isEmpty(annotation.value()))
            applicationName = annotation.name();
        else
            applicationName = annotation.value();
        if (StringUtils.isEmpty(annotation.url())) {
            throw new CustomerOpenFeignLBException(1003, "url in FeignClient annotation can not be empty");
        }
        path = annotation.path();
    }

    @Override
    public String getApplicationName() {
        return applicationName;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    protected String getPath() {
        return path;
    }
}
