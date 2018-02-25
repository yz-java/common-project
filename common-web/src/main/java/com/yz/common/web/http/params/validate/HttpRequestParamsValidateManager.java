package com.yz.common.web.http.params.validate;

import com.yz.common.web.annotations.ParamsValidate;
import org.reflections.Reflections;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yangzhao
 * @Description
 * @Date create by 22:51 18/2/14
 */
@Component
public class HttpRequestParamsValidateManager implements BeanFactoryPostProcessor {

    private final Logger logger = LoggerFactory.getLogger(HttpRequestParamsValidateManager.class);

    private String pacakgePath = "com";

    public void setPacakgePath(String pacakgePath) {
        this.pacakgePath = pacakgePath;
    }

    public HttpRequestParamsValidateManager() {
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //getconfigs
        List<String> configs = getConfigs().stream().collect(Collectors.toList());

        //打印所有生成的expression配置信息
        configs.forEach(s -> logger.info(s));

        //设置aop信息
        setAopInfo(configs,beanFactory);
    }

    /**
     * 设置注册bean动态AOP信息
     *
     * @param configs
     * @param beanFactory
     */
    private void setAopInfo(List<String> configs, ConfigurableListableBeanFactory beanFactory) {

        if (beanFactory instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
            for (String config : configs) {
                //增强器
                RootBeanDefinition advisor = new RootBeanDefinition(DefaultBeanFactoryPointcutAdvisor.class);
                advisor.getPropertyValues().addPropertyValue("adviceBeanName", new RuntimeBeanReference("httpParamsValidateAdvisor").getBeanName());
                //切点类
                RootBeanDefinition pointCut = new RootBeanDefinition(AspectJExpressionPointcut.class);
                pointCut.setScope(BeanDefinition.SCOPE_PROTOTYPE);
                pointCut.setSynthetic(true);
                pointCut.getPropertyValues().addPropertyValue("expression", config);

                advisor.getPropertyValues().addPropertyValue("pointcut", pointCut);
                //注册到spring容器
                String beanName = BeanDefinitionReaderUtils.generateBeanName(advisor, beanDefinitionRegistry, false);
                beanDefinitionRegistry.registerBeanDefinition(beanName, advisor);
            }
        }

    }

    public Set<String> getConfigs() {
        Set<String> configs = new HashSet<>();
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(pacakgePath)).setScanners(new MethodParameterScanner()));
        Set<Method> methodsAnnotatedWith = reflections.getMethodsWithAnyParamAnnotated(ParamsValidate.class);
        Iterator<Method> it = methodsAnnotatedWith.iterator();
        while (it.hasNext()) {
            Method method = it.next();
            String classAndMethod = method.getDeclaringClass().getCanonicalName() + "." + method.getName();
            //生成expression配置
            String expression = "execution (* " + classAndMethod + "(..))";
            configs.add(expression);
        }
        return configs;
    }

}
