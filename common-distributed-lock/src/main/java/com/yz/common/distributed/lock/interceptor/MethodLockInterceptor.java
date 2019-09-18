package com.yz.common.distributed.lock.interceptor;

import com.yz.common.distributed.lock.annotation.MethodLock;
import com.yz.common.distributed.lock.DistributedLock;
import com.yz.common.distributed.lock.MethodLockConfigBeanNameUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author: yangzhao
 * @Date: 2019/9/14 17:25
 * @Description:
 */
public class MethodLockInterceptor implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MethodLockInterceptor.class);

    @Autowired
    @Qualifier(MethodLockConfigBeanNameUtils.METHOD_DISTRIBUTED_LOCK_BEAN_NAME)
    private DistributedLock distributedLock;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        MethodLock annotation=null;
        String lockKey="";
        boolean lock=false;
        try {
            Method currentMethod = methodInvocation.getMethod();
            Object[] arguments = methodInvocation.getArguments();
            Object target = methodInvocation.getThis();
            annotation = currentMethod.getAnnotation(MethodLock.class);
            String[] paramterNames = getParamterNames(currentMethod);
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(annotation.key());
            EvaluationContext context = new StandardEvaluationContext();
            for(int i=0;i<arguments.length;i++){
                context.setVariable(paramterNames[i],arguments[i]);
            }
            String value = expression.getValue(context, String.class);
            String methodName=target.getClass().getName()+"."+currentMethod.getName();
            logger.info("method lock handle: "+methodName);
            lockKey=methodName+":"+value;
            long time = annotation.time();
            int attemptNum = annotation.attemptNum();
            TimeUnit timeUnit = annotation.timeUnit();
            lock = distributedLock.lock(lockKey,time, timeUnit,attemptNum);
            if (!lock){
                logger.info("方法名====>"+methodName+" 未获取到锁");
                throw new RuntimeException("获取方法锁失败");
            }
            return methodInvocation.proceed();
        }finally {
            if (annotation!=null&&annotation.autoUnLock()&&lock){
                distributedLock.unlock(lockKey);
            }
        }
    }

    public String[] getParamterNames(Method method){
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        return  u.getParameterNames(method);
    }
}
