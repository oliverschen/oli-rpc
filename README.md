

## 手写一个自己的 RPC 框架

代码仓库：[oli-rpc](https://github.com/oliverschen/oli-rpc)

### 目的

- [ ] Netty 的实战使用
- [ ] 深入理解 RPC 框架的调用过程和原理
- [ ] 完成个自己的 RPC 框架 ^_^

### 过程

**RPC**：一般稍微复杂的业务都会存在服务间的调用，比如 A 系统调用 B 系统，如果使用 HttpClient 或者 OkHttp 这样的第三方框架，也是可以完成 A 服务远程调用 B 服务，这样就通过 HTTP 协议完成了一次 RPC 调用。

<img src="https://github.com/oliverschen/oli-rpc/blob/main/doc/image/v1.0.0-a-b.png" alt="HTTP远程调用" style="zoom:50%;" />

如果系统和业务不复杂，这样直接调用是完全没有问题的。但如果业务比较复杂，调用的时候写的一堆无关的代码，就显得特别冗杂，而且服务之间调用会越来越乱，很难分清楚模块之间的调用逻辑，比如下面存在冗余的代码：

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

RPC 框架则是将这些重复的调用代码封装起来，像调用本地服务一样调用远程的服务，并提供服务治理，容错等功能。

#### 步骤

1. 客户端代理存根，确认数据服务的寻址方式「如何找到调用的是哪个服务的那个类的那个方法，具体有哪些参数等」
2. 客户端序列化和反序列化数据
3. 网络通讯
4. 服务端接序列化和反序列化数据
5. 服务端代理存根
6. 服务端调用目标服务，将数据序列化原路返回

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

1. 严重依赖 Tomcat 等 Servlet 容器，服务端 **必须** 要实现消费端调用的默认接口。
2. 没有注册中心，consumer 调用 provider 时地址是写死的
3. 代理方式单一，HTTP 方式性能相对比较低等等

### V1.0.3

**使用 Netty 进行远程调用。添加 Redis 作为注册中心** 。[V1.0.3 代码地址](https://github.com/oliverschen/oli-rpc/releases/tag/v1.0.3)

#### 结构图

<img src="https://github.com/oliverschen/oli-rpc/blob/main/doc/image/v1.0.3.png" style="zoom:50%" alt="v1.0.3结构图"/>

#### 包结构

<img src="https://github.com/oliverschen/oli-rpc/blob/main/doc/image/package.png" style="zoom:50%" alt="v1.0.3 包结构"/>

#### 流程

##### 公共包「oli-rpc-common」

公共包，主要是数据协议，工具类等通用类。

##### 注册中心「oli-rpc-register」

1. 考虑到以后会引入多个注册中心「Zookeeper，Nacos 等」，定义好注册中心接口 `Register` ，Redis 注册中心 `RedisRegister` 实现注册中心接口和 `InitializingBean` 在注册中心初始化后创建 Redis 客户端。

```java
@Override
public void afterPropertiesSet() {
    // todo 如果配置了多个地址，用 , 号分隔，需要实现 cluster 客户端
    this.redisClient = new SingleRedisClient(oliProperties.getRedis());
}
```

2. 注册中心服务是以 Hash 结构来存储的，这里值暂时设置成了权重，多个注册中心时用来标记各自调用权重

```properties
key: com.github.oliverschen.olirpc.api.UserService # 服务 key
field:http://127.0.0.1:7777@@class com.github.oliverschen.olirpc.service.UserServiceImpl # 服务详细信息
value:0 # weight：权重
```

##### 序列化「oli-rpc-serialization」

序列化包，分出来的原因也是考虑到以后要支持多种序列化方式，目前只支持 json 序列化，使用的是 Jackson 包。

##### 远程调用「oli-rpc-remote」

远程调用包，包结构参考了 Dubbo 的结构，但是比它要简单很多，目前暂时支持 HTTP 和 TCP 两种形式，前者还是 V1.0.0 版本，依赖 Servlet 容器进行调用，后者使用 Netty 完成客户端和服务端之间的交互。

Netty 实现客户端：

1. Netty 入门还是挺复杂的，不过入门之后写代码对于 NIO 代码来说还是挺简单的，都是些模板代码。Netty 中对于数据的「出」和「入」要站在当前的端来看，比如一个数据从客户端发送到服务端，对于客户端来说是「出站」，但是对于服务端来说是「入站」。反之，服务端通知一个数据到客户端，对于服务端来说就是「出站」，对于客户端来说就是「入站」。Netty 客户端和服务端初始化代码基本都没有什么大的变化，就是实现数据出入站的 Handler 时，要站在当前角色出发考虑。
2. Netty 是通过 TCP 协议进行二进制数据传输，所以在 Netty 客户端和服务端交互的时候，客户端现将消息「编码」发送到服务端，服务端收到消息之后要进行「解码」处理完数据后服务端将消息「编码」发送给客户端，客户端收到后「解码」消息返回。

客户端连接的主要逻辑：

```java
// ... 省略初始化代码
public OliNetty connect() {
    this.group = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap()
            .group(group)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                  	// 编解码器，代码层面要放在自定义 handler 前面
                    pipeline.addLast(new NettyDecoder(OliResp.class, new JsonSerialization()));
                    pipeline.addLast(new NettyEncoder(OliReq.class, new JsonSerialization()));
                    pipeline.addLast(new NettyClientHandler());
                }
            });
    try {
        // sync() Netty 是异步调用，这里 sync() 表示连接确保完成才往后执行
        ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
        channel = channelFuture.channel();
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        log.error("oli-rpc netty client bind error:", e);
    }
    log.info("netty client connect success");
    return this;
}

// 发送代码
public OliResp send(OliReq req) {
    // 绑定 request 和 future 对象
    FUTURE.put(req.getId(),new OliRpcFuture());
    // 将调用信息给服务端
    channel.writeAndFlush(req);
    // 异步转同步，等待数据返回
    OliRpcFuture result = FUTURE.get(req.getId());
    return result.getResp();
}
```

`NettyClientHandler` 处理器主要逻辑：

```java
// 客户端接收到服务端消息时会回调这里
protected void channelRead0(ChannelHandlerContext ctx, OliResp msg) {
    log.info("response info :{}", msg);
  	// netty 是异步调用，借鉴了 Dubbo 的处理方式，将请求用唯一 id 的形式绑定 request 和 response，通过 id 就可以找到对应的 response 消息内容返回给客户端
    OliRpcFuture future = FUTURE.get(msg.getId());
    future.setResp(msg);
    log.info("bind response to future complete");
}
```

`OliRpcFuture` 完成异步转同步操作，主要逻辑：

```java
// 数据返回时设置数据到当前 future，唤醒等待线程
public void setResp(OliResp resp) {
    if (isSucceed) {
        return;
    }
    synchronized (lock) {
        this.resp = resp;
        this.isSucceed = true;
        lock.notifyAll();
    }
}
// 循环获取消息
public OliResp getResp() {
    log.info("invoke netty server resp() method");
    synchronized (lock) {
        while (!isSucceed) {
            try {
                log.info("wait netty server result come back");
                lock.wait(5000);
            } catch (InterruptedException e) {
                log.error("get response error:", e);
                Thread.currentThread().interrupt();
            }
        }
        return resp;
    }
}
```

Netty 服务端：

`OliNettyServer` 服务端和客户端的逻辑差不多

```java
// ... 省略初始化代码
public void start() {
    ServerBootstrap bootstrap = new ServerBootstrap()
            .group(bossGroup(), workGroup())
            .option(ChannelOption.SO_BACKLOG, 1024)
            .channel(NioServerSocketChannel.class)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    // 和客户端一样的编解码逻辑
                    pipeline.addLast(new NettyEncoder(OliResp.class, new JsonSerialization()));
                    pipeline.addLast(new NettyDecoder(OliReq.class, new JsonSerialization()));
                    pipeline.addLast(new NettyServerHandler());
                }
            });
    try {
        log.info("oli-rpc netty server starting");
        ChannelFuture channelFuture = bootstrap.bind(NETTY_SERVER_DEFAULT_PORT).sync();
        log.info("oli-rpc netty server started");
        // 阻塞，等待关闭事件才会关闭服务端
        channelFuture.channel().closeFuture().sync();
    } catch ( InterruptedException e) {
        log.error("oli-rpc netty server start error:", e);
        Thread.currentThread().interrupt();
    }
}
```

`NettyServerHandler` 服务端处理器主要逻辑：

```java
// 服务端从客户端读取消息逻辑
protected void channelRead0(ChannelHandlerContext ctx, OliReq msg) throws Exception {
    OliResp resp;
    log.debug("request data :{}", msg);
    // 通过 OliInvoker 反射调用具体的实现类
    resp = OliInvoker.invoke(msg);
    log.info("invoke result:{}", resp);
    resp.setId(msg.getId());
  	// 消息回写给客户端
    ctx.writeAndFlush(resp);
}
```

`OliInvoker` 反射调用主要逻辑：

```java
public static OliResp invoke(OliReq req) {
    Object bean = OliRegistryPostProcessor.getBean(req.getService());
    Method[] methods = bean.getClass().getMethods();
    // 过滤出来目标方法
    Method method = Arrays.stream(methods)
            .filter(m -> m.getName().equals(req.getMethod()))
            .findFirst()
            .orElseThrow(() -> new OliException(req.getService() + ": " + req.getMethod() + " not find"));
    try {
        // 调用目标方法
        Object invoke = method.invoke(bean, req.getParams());
        // 序列化后返回
        return OliResp.ok(MAPPER.writeValueAsString(invoke));
    } catch (IllegalAccessException | InvocationTargetException | JsonProcessingException ex) {
        log.error("can't find the service method:", ex);
        return OliResp.error("can't find the method:" + req.getService() + req.getMethod(), ex);
    }
}
```

##### 核心包「oli-rpc-core」

主要负责集合其他包，生成客户端代理对象，`OliRefer` 将要调用的方法生成代理对象，主要逻辑：

```java
// 创建要代理的目标类和定义返回结果
public <T,R> T create(Class<T> serviceClass, Class<R> result) {
    // 获取注册中心的服务
    Set<Object> services = redisRegister.obtainServices(serviceClass.getName());
    List<Object> list = new ArrayList<>(services);
    // 随机取一个服务调用
    String service = (String) list.get(ThreadLocalRandom.current().nextInt(list.size()));
    String[] split = service.split(JOINER);
    log.info("random service url is :{}",split[0]);
    // 为了支持多种代理方式，抽离出 OliProxy，目前支持 JDK 和 ByteBuddy 两种方式
    Object o = OliProxy.init(oliProperties).create(serviceClass, split[0], result);
    return (T) o;
}
```

JDK Proxy 主要代理逻辑，`JdkProxy` 类实现 InvocationHandler 接口：

```java
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    OliReq req = buildOliReq(serviceClass, method, args);
    log.info("动态代理 invoke 信息：{}", req);
    OliResp oliResp = OliRpcRemoteBase.init0(url, NETTY_SERVER_DEFAULT_PORT, protocol)
            .send(req);
    return oliResp != null ? MAPPER.readValue(oliResp.getData().toString(), this.result) : null;
}
```

ByteBuddy 主要代理逻辑，`ByteBuddyProxy` 类：

```java
// ByteBuddy 是个很好玩的代理类库，API 使用简单，并且其性能也很优越，详细可以看下官网
public Object createInstance(Class<T> serviceClass) throws NoSuchMethodException, IllegalAccessException,
        InvocationTargetException, InstantiationException {
     return new ByteBuddy().subclass(serviceClass)
            .method(isDeclaredBy(serviceClass))
       			// 方法拦截器
            .intercept(MethodDelegation.to(new MethodInterceptor<>(serviceClass,url,result,protocol)))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded()
            .getDeclaredConstructor()
            .newInstance();
}
```

拦截器 `MethodInterceptor` 主要逻辑：

```java
@RuntimeType
public Object intercept(@AllArguments Object[] allArguments,
                        @Origin Method method) throws IOException {
    OliReq req = buildOliReq(serviceClass, method, allArguments);
    log.info("动态代理 invoke 信息：{}", req);
    OliResp oliResp = OliRpcRemoteBase.init0(url, NETTY_SERVER_DEFAULT_PORT, protocol)
            .send(req);
    return MAPPER.readValue(oliResp.getData().toString(), this.result);
}
```

##### 服务端

provider 启动的时候启动 oli-rpc 服务端：

```java
@SpringBootApplication
public class OliRpcProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OliRpcProviderApplication.class, args);
        // 启动 rpc 服务
        OliNettyServer.init().start();
    }
}
```

##### 客户端

consumer 调用逻辑：

```java
@RestController
public class UserController {

    @Autowired
    private OliRefer oliRefer;

    @GetMapping("/user/{id}")
    public User user(@PathVariable("id") Integer id) {
        // 通过 OliRefer 创建代理并且指定返回对象
        UserService userService = oliRefer.create(UserService.class, User.class);
        return userService.findById(id);
    }
}
```

#### 结果

使用 postman  测试结果：`http://localhost:6666/user/1`

### 测试

用压测工具 `wrk` 进行第一次压测
> wrk -c 100 -t 100 -d 30s 'http://localhost:6666/user'

使用 netty 进行调用，发生异常，系统打开文件数限制

> java.io.IOException: Too many open files

我用的是 Mac bigsur 版本，查看最大打开文件数量
> launchctl limit

maxfiles 就是最大打开文件数量
> ➜  ~ launchctl limit
  	cpu         unlimited      unlimited
  	filesize    unlimited      unlimited
  	data        unlimited      unlimited
  	stack       8388608        67104768
  	core        0              unlimited
  	rss         unlimited      unlimited
  	memlock     unlimited      unlimited
  	maxproc     2784           4176
  	maxfiles    256            unlimited 

修改步骤
https://blog.abreto.net/archives/2020/02/macos-too-many-open-files.html

我这里没有改系统配置，改小了压测时间周期「其实这样的压测意义不大」
> ➜  ~ wrk -c 1 -t 1 -d 10s 'http://localhost:6666/user'
>  Running 10s test @ http://localhost:6666/user
>    1 threads and 1 connections
>    Thread Stats   Avg      Stdev     Max   +/- Stdev
>      Latency    12.21ms   34.34ms 312.81ms   97.16%
>      Req/Sec   106.47     82.46   232.00     52.63%
>    210 requests in 10.03s, 35.86KB read
>    Socket errors: connect 0, read 0, write 0, timeout 4
>    Non-2xx or 3xx responses: 4
>  Requests/sec:     20.94
>  Transfer/sec:      3.58KB

### 总结

从简单到复杂，在有具体目标需求的时候，用需求驱动开发。在这个过程中学到的东西和采坑是最不容易忘记的。哈哈，虽然市面上有很多很成熟身经百战的 RPC 框架，但是自己动手可以对这些框架有更深层次的理解，也是一件一举多得的事情。另外这个东西还是很简陋，还有很多很多需要改进的地方，不管在代码还是设计上，会一点点的优化上去，后续考虑有下面几点：

1. 多个服务提供者时选取算法「按权轮询，随机，一致性哈希等」
2. 内置 Servlet 容器「Tomcat-ember」
3. 完善序列化方式(目前只支持json序列化方式)
5. ~~⭐️使用注解的方式使用组件「@EnableOliRpc，@OliService，@OliRefer 参考 Dubbo」~~
6. ⭐️容错机制等等
7. 框架设计需要考虑的因素总结
8. 每个阶段出压测报告
9. ~~SPI 加载机制~~





