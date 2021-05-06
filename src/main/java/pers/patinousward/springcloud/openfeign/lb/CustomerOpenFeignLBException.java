package pers.patinousward.springcloud.openfeign.lb;

/**
 * @Author patinousward
 * @create 2021/4/30 下午4:30
 */
public class CustomerOpenFeignLBException extends RuntimeException {

    private int code;

    public CustomerOpenFeignLBException(int code, String msg) {
        super(msg);
        this.code = code;
    }

}
