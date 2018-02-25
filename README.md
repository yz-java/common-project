# common-project

Java后端常用工具类、缓存接口、消息队列接口、第三方支付接口封装；Restful接口参数验证，错误信息友好提示。

[TOC]

## 1.缓存(common-cache)：

① 基于JVM方法区数据缓存

② 基于redis进行数据缓存

## 2.消息队列（common-queue）

① 基于Java ArrayBlockingQueue

② 基于redis 发布订阅实现消息队列

▲ 后期会维护kafka和阿里巴巴RocketMQ

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

## 5.微信、支付宝支付（common-pay）

使用PayFactory工厂类统一管理

①创建订单（createOrder）
    
②退款(refund）
    
③生成移动端调起微信和支付宝时所需要的参数（createPayParams）
    
④订单查询（queryOrder）
    
## 6.common-web

①自定义web拦截器（DataSafeFilter）

    加密json数据解析为Map保存到HttpServletRequest中；自定义HttpServletRequest包装类
    
    （IHttpServletRequestWrapper），将解析的Map数据以请求参数方式保存到Parameter中


②基于springmvc restful接口参数验证，返回前端友好提示。

QQ:208275451
邮箱：yangzhao_java@163.com


