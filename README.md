# common-project

Java后端常用工具类、缓存接口、消息队列接口、第三方支付接口封装；Restful接口参数验证，错误信息友好提示。

[TOC]

## 1.分布式锁、分布式方法锁(common-distributed-lock)：

### DistributedLock类（依赖RedisTemplate接口实现）
```

    /**
     *
     * @param key 锁标识
     * @param attempt 重试次数
     * @return
     */
    public boolean lock(String key,int attempt);

    /**
     * 
     * @param key 锁标识
     * @param time 过期时间
     * @param timeUnit 过期时间单位
     * @param attemptNum 重试次数
     * @return
     */
    public boolean lock(String key, long time, TimeUnit timeUnit,int attemptNum)
```

### @EnableMethodLock @MethodLock 使用
#### @EnableMethodLock

**在Spring Boot 项目启动类添加该注解开启分布式方法锁**

**普通Spring项目配置component-scan自动扫描com.yz.common.distributed.lock.configuration包即可**

#### @MethodLock

```
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
```

在需要使用分布式方法锁的method上使用该注解即可



## 2.ES（common-elasticsearch）

① ElasticSearch 增删改操作

② ElasticSearch 高级查询（链式调用）

## 3.常用工具类(common-core)

网络类(HttpUtil)

时间类(DateUtils)

JSON类(JsonUtil)

安全类(MD5加密、AES加密、Base64编码、3DES加密、RSA加密、SHA256)

图片处理(GraphicsMagick)

...

## 4.Redis（common-redis）

①redis常用API接口(RedisUtil)

②基于redis实现分布式锁(RedisLockUtil)

## 5.支付模块（common-pay）

①使用简单工厂模式实现预支付订单生成（位于com.yz.common.payment.trade.pay包下）

![image.png](https://upload-images.jianshu.io/upload_images/3057341-77da3dce07ca5e42.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

②使用建造者模式对预支付订单参数进行动态构建（位于com.yz.common.payment.trade.pay.builder包下）

![image.png](https://upload-images.jianshu.io/upload_images/3057341-b31a5e2f5428a365.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

③通过ThirdPayFactory工厂类获取PayService

![image.png](https://upload-images.jianshu.io/upload_images/3057341-240fecbbe6264c28.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

④发起预支付订单、退款、订单查询调用

## 6.common-web

①请求参数转换（非Form表单或get+参数方式）：

    自定义web拦截器（DataSafeFilter），加密json数据解析为Map保存到HttpServletRequest
    中；自定义HttpServletRequest包装类（IHttpServletRequestWrapper），将解析的Map数        
    据保存到Parameter中


②基于hibernate-validator restful接口参数验证
