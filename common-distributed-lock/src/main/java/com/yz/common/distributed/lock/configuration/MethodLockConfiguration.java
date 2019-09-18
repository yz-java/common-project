package com.yz.common.distributed.lock.configuration;

import com.yz.common.distributed.lock.annotation.MethodLock;
import com.yz.common.distributed.lock.DistributedLock;
import com.yz.common.distributed.lock.MethodLockConfigBeanNameUtils;
import com.yz.common.distributed.lock.interceptor.MethodLockInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yangzhao
 * @Date: 2019/9/14 17:18
 * @Description:
 */
@Configuration
public class MethodLockConfiguration {

    @Bean(name = MethodLockConfigBeanNameUtils.METHOD_DISTRIBUTED_LOCK_POINTCUT_ADVISOR_BEAN_NAME)
    public DefaultPointcutAdvisor defaultBeanFactoryPointcutAdvisor(){
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
        defaultPointcutAdvisor.setAdvice(methodLockInterceptor());
        defaultPointcutAdvisor.setPointcut(pointcut());
        return defaultPointcutAdvisor;
    }

    @Bean
    public MethodLockInterceptor methodLockInterceptor(){
        return new MethodLockInterceptor();
    }

    @Bean
    public Pointcut pointcut(){
        return new AnnotationMatchingPointcut(null,MethodLock.class);
    }

    @Bean(name = MethodLockConfigBeanNameUtils.METHOD_DISTRIBUTED_LOCK_BEAN_NAME)
    public DistributedLock distributedLock(){
        return new DistributedLock();
    }
}
