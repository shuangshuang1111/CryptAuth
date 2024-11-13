package com.shuangshuan.cryptauth.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class SqlLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SqlLogAspect.class);

    @Before("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    public void logSql(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.info("Executing query with parameters: {}", Arrays.toString(args));
    }
}
