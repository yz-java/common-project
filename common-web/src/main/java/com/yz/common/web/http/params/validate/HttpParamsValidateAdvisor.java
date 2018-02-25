package com.yz.common.web.http.params.validate;

import com.alibaba.fastjson.JSON;
import com.yz.common.core.configuration.ValidatorConfiguration;
import com.yz.common.core.exception.HandlerException;
import com.yz.common.core.http.ResponseMessage;
import com.yz.common.core.utils.ClassUtil;
import com.yz.common.web.annotations.HttpParam;
import com.yz.common.web.annotations.ParamsValidate;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 增强类
 * 实现MethodInterceptor接口，通过反射动态解析方法是否标注@DataSource {@link ParamsValidate}注解。
 * @author yangzhao
 *         create by 17/10/20
 */
@Component("httpParamsValidateAdvisor")
public class HttpParamsValidateAdvisor implements MethodInterceptor {

    private final Logger logger = LoggerFactory.getLogger(HttpParamsValidateAdvisor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object[] arguments = methodInvocation.getArguments();
        Parameter[] parameters = method.getParameters();
        for (int i=0;i<parameters.length; i++) {
            ParamsValidate annotation = parameters[i].getAnnotation(ParamsValidate.class);
            if (annotation!=null){
                Set<ConstraintViolation<Object>> violationSet = ValidatorConfiguration.validator().validate(arguments[i]);
                for (ConstraintViolation model : violationSet) {
                    logger.warn(model.getPropertyPath()+model.getMessage());
                    return JSON.toJSONString(new ResponseMessage(-1,model.getPropertyPath()+model.getMessage()));
                }
            }
        }

        Object proceed = null;
        try {
            proceed = methodInvocation.proceed();
        }catch (Exception e){
            throw e;
        }
        return proceed;
    }

    private void validate(Object argument) throws HandlerException {
        Class<?> aClass = argument.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        List<String> nullParams = new ArrayList<>();
        for (Field field :declaredFields)
        {
            HttpParam annotation = field.getAnnotation(HttpParam.class);
            if (annotation==null){
                continue;
            }

            String methodName = ClassUtil.createFieldMethodName("get", field.getName());
            Method declaredMethod = null;
            try {
                declaredMethod = aClass.getDeclaredMethod(methodName);
                Object invoke = declaredMethod.invoke(argument);
                if (invoke==null){
                    nullParams.add(field.getName());
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        if (nullParams.size()>0){
            String collect = nullParams.stream().collect(Collectors.joining("、"));
            throw new HandlerException("请求参数"+collect+"不能为空");
        }

    }
}
