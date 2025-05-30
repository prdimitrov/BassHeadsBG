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

    private static final String TARGET = "{}";
    private static final String EXECUTION_COM_BG_BASSHEADSBG_INIT = "execution(* com.bg.bassheadsbg.init..*(..))";
    private static final String INITIALIZATION_TASK_MESSAGE = "Initialization task is about to start in class: " + TARGET + " and method: " + TARGET;

    @Before(EXECUTION_COM_BG_BASSHEADSBG_INIT)
    public void logBeforeInitialization(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.info(INITIALIZATION_TASK_MESSAGE, className, methodName);
    }
}