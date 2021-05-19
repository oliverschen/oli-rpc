## 手写一个自己的 RPC 框架

### 目的

- [ ] Netty 的实战使用
- [ ] 深入理解 RPC 框架的调用过程和原理
- [ ] 完成个自己的 RPC 框架 ^_^

### 过程

**RPC**：一般稍微复杂的业务都会存在服务间的调用，比如 A 系统调用 B 系统，如果使用 HttpClient 或者 OkHttp 这样的第三方框架，也是可以完成 A 服务远程调用 B 服务，这样就通过 HTTP 协议完成了一次 RPC 调用。

<img src="https://github.com/oliverschen/oli-rpc/blob/main/doc/image/v1.0.0-a-b.png" alt="HTTP远程调用" style="zoom:50%;" />

这样直接调用也是可以完成的，但在调用的时候要写一堆无关的代码，比如下面：

```java
public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

OkHttpClient client = new OkHttpClient();

String post(String url, String json) throws IOException {
  RequestBody body = RequestBody.create(JSON, json);
  Request request = new Request.Builder()
      .url(url)
      .post(body)
      .build();
  try (Response response = client.newCall(request).execute()) {
    return response.body().string();
  }
}
```

而 RPC 框架则是将这些重复的调用代码封装起来，像调用本地服务一样调用远程的服务。

#### 步骤

1. 生成客户端代理对象，确认数据服务的寻址方式「如何找到调用的是哪个服务的那个类的那个方法，具体有哪些参数等」
2. 序列化数据
3. TCP 网络传输
4. 服务端接收数据，将数据反序列化
5. 服务端进行业务处理，将数据序列化原路返回

<img src="https://github.com/oliverschen/oli-rpc/blob/main/doc/image/v1.0.0-c-s-n.png" alt="z" style="zoom:50%;" />

### V1.0.0

**依赖 Web 容器，使用 HTTP 协议 + 动态代理的方式实现 RPC 服务。**[V1.0.0代码地址](https://github.com/oliverschen/oli-rpc/releases/tag/v1.0.0)

#### 结构图

<img src="https://github.com/oliverschen/oli-rpc/blob/main/doc/image/version1.0.0.png" style="zoom:50%" alt="v1.0.0结构图"/>

#### 流程

##### 服务端

1. 依赖 Spring 的 IoC 容器，在 bean 初始化「 BeanPostProcessor 实现」完成之后，将所有 @Service 注解的 bean 注册在自定义的 RPC 容器中。
2. 暴露消费端要请求的默认接口地址
3. 序列化接收到的消息「这里依赖 SpringMVC 的序列化能力」
4. 通过接收到的消息，找到要调用的类和方法，使用反射的方式调用执行，并返回结果

##### 消费端

1. 使用动态代理，封装要请求的服务端的地址
2. 组装约定好的数据请求和返回格式
3. 反序列化返回的数据「json」

#### 弊端

1. 严重依赖 Tomcat 等 Servlet 容器，服务端必须要实现消费端调用的默认接口。
2. 没有注册中心，consumer 调用 provider 时地址是写死的
3. 代理方式单一，HTTP 方式性能相对比较低

