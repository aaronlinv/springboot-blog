package com.al.blog.aspect;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// 切面
@Aspect
// 开启组件扫描
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 包下 所有类的所有方法
    @Pointcut("execution(* com.al.blog.web.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore() {
        logger.info("------------doBefore-------------");
    }

    @After("log()")
    public void doAfter() {
        logger.info("------------doAfter-------------");
    }

    @AfterReturning(returning = "result",pointcut = "log()") // 就是下面这个
    public void doAfterReturn(Object result) {
        logger.info("result : {}",result);
    }
    
    
}
