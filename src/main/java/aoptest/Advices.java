package aoptest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 通知类，横切逻辑
 *
 */
@Component
@Aspect
public class Advices {

    @Before("execution(* aoptest.Math.*(..))")
    public void before(JoinPoint jp){
        System.out.println("----------前置通知----------");
        for (int i = 0; i < jp.getArgs().length; i++) {
            System.out.println(jp.getArgs()[i]);
        }
        System.out.println(jp.getSignature().getName());
    }
    @After("execution(* aoptest.Math.*(..))")
    public void after(JoinPoint joinPoint){
            System.out.println("----------最终通知----------");
    }
    @Around("execution(* aoptest.Math.*(..))")
    public Object  doBasicProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object o = proceedingJoinPoint.proceed();
        System.out.println("----环---:"+o);
        return o;
    }
}
