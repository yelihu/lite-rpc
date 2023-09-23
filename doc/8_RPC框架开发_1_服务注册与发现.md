# RPC服务开发：服务注册与发现

1. 为什么需要服务注册与发现？注册中心、调用方和提供方三者之间的关系是什么
2. 服务调用方和服务提供方之间调用的契约是什么？
3. 服务订阅从注册中心哪里得到的是什么数据？
4. 基于Zookeeper的服务发现是如何实现的？
5. Watcher机制是如何实现服务更新**实时变更推送**的？
6. 服务发现的本质是什么，维护的是什么**数据之间的mapping**
7. 实现服务注册与发现功能的思路是什么？
   1. 服务平台管理端 *Manager* 是什么，职责是什么？
   2. Zookeeper集群是什么的职责是什么？
   3. 服务调用者的职责是什么？
   4. 服务提供者的职责是什么？
8. 服务平台管理端 *Manager* 创建的服务根路径是什么，有什么含义？
9. 路径下的为什么要保存服务提供方和服务调用方的列表目录？
10. 服务提供方注册的时候
    1. 创建的是什么持久化类型的节点，为什么？
    2. 存储的是什么信息？
11. 服务调用方订阅的时候
    1. 创建的是什么持久化类型的节点，为什么？
    2. 存储的是什么信息？
    3. 为什么使用Watcher机制，Watch的是什么变化？
12. 服务提供方的数据变更之后，Zookeeper会做什么？
13. Zookeeper是什么模型，会存在什么性能问题？除了Zookeeper还有什么其他厂商的开源注册中心，他们的解决方案是什么？

## Maven代码结构

1. framework内部各个模块的职责分别是什么？
1. demo里面的模块的职责分别是什么？

### framework

<img src="assets/image-20230923201433554.png" alt="image-20230923201433554" style="zoom: 25%;" />



```xml
<modules>
    <module>lite-rpc-demo</module>
    <module>lite-rpc-framework</module>
    <module>lite-rpc-framework/lite-rpc-common</module>
    <module>lite-rpc-framework/lite-rpc-core</module>
    <module>lite-rpc-registry</module>
</modules>
```



### demo

```xml
<modules>
  <module>lite-rpc-demo-consumer</module>
  <module>lite-rpc-demo-provider</module>
  <module>lite-rpc-demo-api</module>
</modules>
```



### 参考

<img src="/Users/apple/Library/Application Support/typora-user-images/image-20230923175546321.png" alt="image-20230923175546321" style="zoom:33%;" />



## 服务端



## 客户端

1. 代理做了什么事情
1. 
