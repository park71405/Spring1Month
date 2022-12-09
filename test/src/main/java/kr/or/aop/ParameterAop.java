package kr.or.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ParameterAop {

	// controller 패키지의 하위 메소드를 모두 Aspect로 설정
    @Pointcut("execution(* com.juhyun.springaop.aop.controller..*.*(..))")
    private void cut() {

    }
    
    // cut() 메소드가 실행되는 지점의 이전에 before() 메소드 수행
    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("--------------- @Before ---------------");
        System.out.println(joinPoint.toString());
        System.out.println(joinPoint.toShortString());
        System.out.println(joinPoint.toLongString());

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println("method: " + method.getName());

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            System.out.println("type: " + arg.getClass().getSimpleName());
            System.out.println("value: " + arg);
        }
    }
    
    // 반환값 확인
    @AfterReturning(value = "cut()", returning = "object")
    public void afterReturn(JoinPoint joinPoint, Object object) {
        System.out.println("--------------- @After ---------------");
        System.out.println(object);
    }
	
}
