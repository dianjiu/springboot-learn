package co.dianjiu.aspect.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Around最先执行
 * 然后在执行proceed方法之前，Before先执行
 * 然后才是方法体本身
 * 然后是Around再结尾
 * 最后才是After
 */
@Component
@Aspect
@Order(10)
public class BermisionAspect {

    @Pointcut("@annotation(co.dianjiu.aspect.annotation.BermisionAnnotation)")
    private void AnnotationPointCut() { }

    /**
     * 定制一个环绕通知
     * @param joinPoint
     */
    @Around("AnnotationPointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around Begin");
        Method method = getMethod(joinPoint);
        BermisionAnnotation logAnnotation = method.getAnnotation(BermisionAnnotation.class);
        String value=logAnnotation.name();//获取注解中的传值
        System.out.println(value);
        joinPoint.proceed();//执行到这里开始走进来的方法体（必须声明）
        System.out.println("Around End");
    }

    private Method getMethod(JoinPoint joinPoint) throws Exception {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method;
    }


    //当想获得注解里面的属性，可以直接注入改注解
    //方法可以带参数，可以同时设置多个方法用&&
    @Before("AnnotationPointCut()")
    public void before(JoinPoint joinPoint) throws Exception {
        Method method = getMethod(joinPoint);
        BermisionAnnotation logAnnotation = method.getAnnotation(BermisionAnnotation.class);
        //通过反射可获得注解上的属性，然后做日志记录相关的操作。
        System.out.println("Before");
    }

    @After("AnnotationPointCut()")
    public void after(JoinPoint joinPoint) throws Exception {
        Method method = getMethod(joinPoint);
        BermisionAnnotation logAnnotation = method.getAnnotation(BermisionAnnotation.class);
        System.out.println("After");
    }

}