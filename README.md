# oli-rpc
oli-rpc 不断迭代成一个功能完备的小型 rpc 调用框架。

## Version1.0.0

实现一个简单的 RPC 框架

### 结构

![version1.0.0 结构图](https://github.com/oliverschen/oli-rpc/blob/main/doc/image/version1.0.0.png)

### 说明
第一个版本采用 HTTP 协议进行服务间通讯，通讯过程：
1. provider 服务提供者将服务暴露在 oli-rpc 容器中。
2. consumer 服务消费者用 JDK Proxy 动态代理的方式生成代理对象
3. 代理对象 invoke() 方法拦截调用，将调用使用 Http 的方式请求远程服务
4. 收到返回结果，处理成对应的返回对象给 consumer

#### 协议
HTTP 协议通讯

#### 序列化
json 序列化方式

## Version1.0.1

- [ ] 添加 Redis 注册中心

### 注册中心


### 结构

![Redis 注册中心结构图](https://github.com/oliverschen/oli-rpc/blob/main/doc/image/redis-registry.png)

#### Redis

第一个注册中心打算用 Redis ，后期会继续将主流的 Zookeeper，nacos 等注册中心完善进来

##### 数据模式

使用 Hash 数据结构，注册中心多服务注册时 Hash 中数据如下：

```bash
com.github.oliverschen.olirpc.UserService   # key

# member
http://localhost:7777@@com.github.oliverschen.olirpc.UserServiceImpl  weight # value
http://localhost:8888@@com.github.oliverschen.olirpc.UserServiceImpl  weight
```

### 说明
1. provider 启动时将服务注册在 Redis 中
2. consumer 消费时在 Redis 中获取这个服务所有的服务，随机取一个调用

#### 缺点
1. 注册没有心跳，服务无法实现自动下线
2. 随机获取服务调用失败，没有失败处理机制

## Version1.0.2
![netty 调用结构图](https://github.com/oliverschen/oli-rpc/blob/main/doc/image/version1.0.2.png)

### 缺点
1. Version1.0.1 之前都是以 HTTP 请求作为调用方式，需要依赖 HTTP 服务器。这里使用在 provider 端暴露根调用入口进行服务的调用。
这样 provider 就必须实现次接口，否则功能无法实现
2. 严重依赖 Web 容器

### 版本说明
1. 添加 netty 作为 RPC 调用的主要方式。
2. consumer 和 provider 都依赖 core 工程，就可以轻松实现 RPC 调用。
3. 在未来可以轻松实现心跳，定制化编码解码等功能

### 待补充
1. 使用注解的方式配置开启和关闭 RPC
2. 完善负载均衡
3. 自适性调用



### 参考文档

> https://dubbo.apache.org/zh/docs/v2.7/dev/source/service-invoking-process/
> https://github.com/withlin/netty-in-action-cn
> https://github.com/diaozxin007/DouRpc