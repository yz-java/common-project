package com.yz.common.core.annotations;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: yangzhao
 * @Date: 2019/7/25 14:34
 * @Description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLock {
    /**
     * 锁标识
     * @return
     */
    String key()  default "";

    /**
     * 时间
     * @return
     */
    long time() default 10;

    /**
     * 单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 是否自动解锁
     * @return
     */
    boolean autoUnLock() default true;

    /**
     * 重试次数
     * @return
     */
    int attemptNum() default 0;

}
