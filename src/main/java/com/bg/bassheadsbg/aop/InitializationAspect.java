package com.bg.bassheadsbg.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class InitializationAspect {

    @Before("execution(* com.bg.bassheadsbg.init..*(..))")
    public void logBeforeInitialization(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.info("Initialization task is about to start in class: {} and method: {}", className, methodName);
    }
}