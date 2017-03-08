package aoptest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 通知类，横切逻辑
 *
 */
/**
 * 可以使用@Order注解指定切面的优先级，值越小优先级越高
 */

@Order(2)
@Component
@Aspect
public class Advices {
    /*每一个方法开始之前执行一段代码*/
    @Before("execution(* aoptest.Math.*(..))")
    public void before(JoinPoint jp){
        System.out.println("----------前置通知----------");
        /*for (int i = 0; i < jp.getArgs().length; i++) {
            System.out.println(jp.getArgs()[i]);
        }
        System.out.println(jp.getSignature().getName());*/
    }

   /* 每一个方法执行之后执行一段代码*/
    @After("execution(* aoptest.Math.*(..))")
    public void after(JoinPoint joinPoint){
            System.out.println("----------最终通知----------");
    }

    /**
     * 环绕通知需要携带ProceedingJoinPoint类型的参数
     * 环绕通知类似于动态代理的全过程：ProceedingJoinPoint类型的参数可以决定是否执行目标方法。
     * 而且环绕通知必须有返回值，返回值即为目标方法的返回值
     */
    @Around("execution(* aoptest.Math.*(..))")
    public Object doBasicProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        String methodName = proceedingJoinPoint.getSignature().getName();
        //执行目标方法
        try {
            //前置通知
            System.out.println( methodName + " 方法参数列表： " + Arrays.asList(proceedingJoinPoint.getArgs()));
            result = proceedingJoinPoint.proceed();
            //返回通知
            System.out.println(methodName + " 方法 返回通知" + result);
        } catch (Throwable e) {
            //异常通知
            System.out.println(methodName + " 异常： " + e);
            throw new RuntimeException(e);
        }
        //后置通知
        System.out.println(methodName + "结束");
        return result;
    }

    /**
     * 方法正常结束后执行的代码
     * 返回通知是可以访问到方法的返回值的
     */
    @AfterReturning(value="execution(* aoptest.Math.*(..))",returning="result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println(methodName + " 返回值： " + result);
    }

    /**
     * 在方法出现异常时会执行的代码
     * 可以访问到异常对象，可以指定在出现特定异常时在执行通知代码
     */
    @AfterThrowing(value = "execution(* aoptest.Math.*(..))", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println( methodName + "抛出异常：" + ex);
    }
}
