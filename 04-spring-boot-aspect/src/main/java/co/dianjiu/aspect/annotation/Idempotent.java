package co.dianjiu.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义幂等注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {
    //自定义的传入多个参数作为组和key，默认的从左到右参数依次拼接为key。参数定义示例：{key1,key2}
    String[] keys() default {};

    //幂等描述
    String desc() default "幂等处理";

    //幂等提示
    String msg() default "不允许请求多次";

    //幂等次数限制
    int limit() default 1;

    //过期时间，单位秒。可以是毫秒，需要修改切点类的设置redis值的代码参数。
    long expirMillis();
}
