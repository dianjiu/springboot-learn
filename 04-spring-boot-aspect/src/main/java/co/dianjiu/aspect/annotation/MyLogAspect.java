package co.dianjiu.aspect.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Slf4j
@Aspect
@Component
public class MyLogAspect {

    // 2. PointCut表示这是一个切点，@annotation表示这个切点切到一个注解上，后面带该注解的全类名
    // 切面最主要的就是切点，所有的故事都围绕切点发生
    // logPointCut()代表切点名称
    @Pointcut("@annotation(co.dianjiu.aspect.annotation.MyLog)")
    public void logPointCut() {
    }

    // 3. 环绕通知
    @Around("logPointCut()")
    public void logAround(ProceedingJoinPoint joinPoint) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        // 获取连接点所在类名
        String classname = joinPoint.getTarget().getClass().getSimpleName();
        // 获取连接点所在方法名称
        String methodName = joinPoint.getSignature().getName();
        // 下面两个数组中，参数值和参数名的个数和位置是一一对应的。
        // 参数名
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        // 参数值
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        //不为空时便利组装
        if (argNames.length > 0 && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                sb.append(argNames[i] + ":" + args[i] + ";");
            }
        }
        long startTime = System.currentTimeMillis();
        log.info("\n进入【" + classname + "." + methodName + "()】方法的时间是：" + sdf.format(startTime) + "\n参数为:" + sb.toString());

        //执行到这里开始走进来的方法体（必须声明）
        try {
            joinPoint.proceed(args);
        } catch (Throwable throwable) {
            log.error("MyLogAspect logAround Exception By Method：" + classname + "." + methodName);
            throwable.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long periodTime = endTime - startTime;
        log.info("\n离开【" + classname + "." + methodName + "()】方法的时间是：" + sdf.format(endTime));
        log.info("\n在【" + classname + "." + methodName + "()】方法中总共耗时为：" + periodTime + " 毫秒");

    }
}
