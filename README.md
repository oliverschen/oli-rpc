# oli-rpc
实现一个简单的 RPC 框架

## Version1.0.0

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
