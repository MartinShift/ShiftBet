package com.example.shiftbet.domain.aspect;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(Loggable)")
    public void loggableMethods() {
    }

    @Before("loggableMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before executing: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "loggableMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("After executing: " + joinPoint.getSignature().toShortString() + ", Result: " + result);
    }

    @AfterThrowing(pointcut = "loggableMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: " + joinPoint.getSignature().toShortString() + ", Exception: " + exception.getMessage());
    }

    @After("loggableMethods()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("After executing: " + joinPoint.getSignature().toShortString());
    }
}
