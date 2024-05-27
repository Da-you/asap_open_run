package com.asap.openrun.global.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedissonLock {

  String value(); // lock의 이름 (고유값)

  long waitTime() default 6000L; // lock 흭득 시도 최대 시간 (ms)

  long leaseTime() default 1000L; // 락 획득 후 점유 최대 시간 (ms)
}
