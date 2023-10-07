## lite-rpc-demo 工程介绍

### 模块说明
- lite-rpc-demo : 父工程
- lite-rpc-demo-consumer ：消费者端
- lite-rpc-demo-provider ：服务提供者端
- lite-rpc-demo-api ：服务提供者端暴露的接口

### 工作流程

1. 服务提供者端将接口的定义和机器上报的注册中心
2. 注册中心维护接口的定义和机器的IP和Port信息
3. 消费者请求注册中心，关于X服务有哪些提供方提供服务
4. 获取到服务提供方的IP和Port, 通过netty发起网络请求
5. 服务提供方处理网络请求，根据接口和入参处理请求并返回结果给消费者端

