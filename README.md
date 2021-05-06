# spring-cloud-openfeign-lb-plugin

## 1.QuickStart
- 1.1 自定义负载均衡规则
> 自定义类继承AbstractLBRule，rule方法中填充自己的负载均衡算法即可，可参考HashLBRule这个实现类


- 1.2 在feign接口中显示调用url，其值为负载均衡规则的name，example:
```
@FeignClient(value= "xxx",url = HashLBRule.NAME)
public interface MyFeign{
}
```
- 1.3 创建bean即可，example:
```
@Configuration
public class CustomizeBeanConfiguration{
    
    @Bean
    public LBRule getLBRule(){
        return new HashLBRule(MyFeign.class,"key")
    }

    @Bean
    public LBRule getLBRule2(){
        return new HashLBRule2(MyFeign2.class,"key")
    }

    xxx

}
```
