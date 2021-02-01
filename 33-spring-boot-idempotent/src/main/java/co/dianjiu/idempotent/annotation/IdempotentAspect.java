package co.dianjiu.idempotent.annotation;

import co.dianjiu.idempotent.exception.IdempotentException;
import co.dianjiu.idempotent.utils.IdempotentKeyUtil;
import co.dianjiu.idempotent.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@Aspect
@ConditionalOnClass(RedisTemplate.class)
public class IdempotentAspect {
    private static final String KEY_TEMPLATE = "idempotent_%S ";

    @Autowired
    RedisUtil redisUtil;

    /**
     * 切点(自定义注解)
     */
    @Pointcut("@annotation(co.dianjiu.idempotent.annotation.Idempotent)")
    public void executeIdempotent() {

    }

    /**
     * 定制一个环绕通知
     *
     * @throws Throwable
     */
    @Around("executeIdempotent()")
    public Object arountd(ProceedingJoinPoint jPoint) throws Throwable {
        //获取当前方法信息
        Method method = getMethod(jPoint);
        //获取注解
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        //获取自定义注解参数
        String[] keys = idempotent.keys();
        String desc = idempotent.desc();
        int limit = idempotent.limit();
        String errMsg = idempotent.errMsg();
        // 参数名
        String[] argNames = getArgNames(jPoint);
        // 参数值
        Object[] args = jPoint.getArgs();


        String key = String.format(KEY_TEMPLATE, IdempotentKeyUtil.generate(method, keys, argNames, args));
        log.info(desc+"的redis的key为"+key);

        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, "0", idempotent.expirMillis(), TimeUnit.SECONDS);

        if (result) {
            return jPoint.proceed();
        } else {
            log.info("数据幂等错误");
            throw new IdempotentException("幂等校验失败。key值为：" + IdempotentKeyUtil.getKeyOriginalString(method, keys, argNames, args));
        }
    }

    private String[] getArgNames(ProceedingJoinPoint jPoint) {
        String[] argNames = ((MethodSignature) jPoint.getSignature()).getParameterNames();
        return argNames;
    }

    private Method getMethod(JoinPoint joinPoint) throws Exception {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return method;
    }

}
