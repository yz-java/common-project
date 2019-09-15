package com.yz.common.distributed.lock.annotation;

import com.yz.common.distributed.lock.configuration.MethodLockConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: yangzhao
 * @Date: 2019/9/14 17:42
 * @Description:
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MethodLockConfiguration.class})
public @interface EnableMethodLock {
}
