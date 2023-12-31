# Lite-RPC

Lite-RPC是一个简单的RPC框架，使用Java语言实现。该框架的目的是为了学习RPC框架和分布式相关的知识，并实现类似于当前流行的RPC框架，如Dubbo等，所提供的一些功能。

## 概述

Lite-RPC框架提供以下功能：

- 实现基本的RPC通信机制，支持远程过程调用。
- 支持服务注册和发现，使客户端能够动态获取可用的服务。
- 支持负载均衡，在多个服务提供者之间进行负载均衡，提高系统的可扩展性和稳定性。
- 支持服务容错和故障恢复，以确保系统的可靠性。
- 提供可扩展的插件机制，以方便进行功能扩展和定制。

Lite-RPC框架整体采用CS架构，整体架构包含如下组件：

* 通信层：负责处理网络通信，包括请求的传输和响应的接受，使用TCP作为网络协议进行通信，使用Netty作为网络通信库。
* 序列化层：响应的序列化和反序列化，支持常见序列化方式。
* 服务注册和发现层：
* 负载均衡层：
* 远程代理层：

### 架构

### 扩展性和可靠性

1. 插件机制、配置机制支持多种序列化、负载均衡策略
2. 使用中心化的服务注册中心来管理服务的注册和发现，提供可靠的服务节点信息，注册中心可以实现高可用确保系统可靠性和稳定性
3. 异常处理，IO异常和超时...
4. 日志和监控

## 项目结构

Lite-RPC项目主要包含以下模块和文件：

1. **lite-rpc-framework**：父项目
2. **lite-rpc-common**：公共模块，包含一些领域建模、VO，通用的工具类和常量。
3. **lite-rpc-core**：核心模块，实现了RPC通信的基本逻辑。
4. **lite-rpc-manager**：管理、治理模块，负责对rpc框架的管理和治理，集群机器的管理和监控。
5. **lite-rpc-registry**：注册中心模块，用于服务的注册和发现。
6. **lite-rpc-remoting**：网络通信层，负责使用netty、zookeeper等底层中间件实现网络通信、节点管理，包含低层通信细节，隔离RPC主流程。

## 文档

### 知识文档

**铺垫知识内容**

* [1_基本知识.md](doc%2F1_%E5%9F%BA%E6%9C%AC%E7%9F%A5%E8%AF%86.md)
* [2_Netty入门.md](doc%2F2_Netty%E5%85%A5%E9%97%A8.md)
* [3_报文协议设计.md](doc%2F3_%E6%8A%A5%E6%96%87%E5%8D%8F%E8%AE%AE%E8%AE%BE%E8%AE%A1.md)

**Zookeeper相关内容**

* [4_Zookeeper基本概念.md](doc%2F4_Zookeeper%E5%9F%BA%E6%9C%AC%E6%A6%82%E5%BF%B5.md)
* [5_在Mac环境的docker安装ZooKeeper.md](doc%2F5_%E5%9C%A8Mac%E7%8E%AF%E5%A2%83%E7%9A%84docker%E5%AE%89%E8%A3%85ZooKeeper.md)
* [6_使用Docker在Mac上搭建Zookeeper集群.md](doc%2F6_%E4%BD%BF%E7%94%A8Docker%E5%9C%A8Mac%E4%B8%8A%E6%90%AD%E5%BB%BAZookeeper%E9%9B%86%E7%BE%A4.md)
* [7_CAP理论和它的朋友们.md](doc%2F7_CAP%E7%90%86%E8%AE%BA%E5%92%8C%E5%AE%83%E7%9A%84%E6%9C%8B%E5%8F%8B%E4%BB%AC.md)
* [8_RPC框架开发_1_服务注册与发现.md](doc%2F8_RPC%E6%A1%86%E6%9E%B6%E5%BC%80%E5%8F%91_1_%E6%9C%8D%E5%8A%A1%E6%B3%A8%E5%86%8C%E4%B8%8E%E5%8F%91%E7%8E%B0.md)

### 用户手册

## 使用方法

1. 克隆Lite-RPC项目到本地：

   ```
   git clone https://github.com/yourUsername/lite-rpc.git
   ```
2. 导入项目到IDE中，并构建项目。
3. 按照需要，修改配置文件，如注册中心地址、服务提供者的IP和端口等。
4. 编写服务接口和实现类，并在服务提供者中注册服务。
5. 在客户端中，通过RPC框架的代理类，调用远程服务。
6. 运行服务提供者和客户端，观察RPC通信的效果和结果。

## 贡献

欢迎对Lite-RPC框架进行改进和扩展。如果您发现了bug，或者有任何建议或想法，请在GitHub上提交issue或者Pull Request。

## 版权和许可

Lite-RPC框架采用MIT许可证，详细信息请参阅 [LICENSE](https://github.com/yourUsername/lite-rpc/blob/master/LICENSE) 文件。

## 参考资料

- [RPC框架原理与实现](https://www.example.com)
- [分布式系统设计](https://www.example.com)
- [Dubbo官方文档](https://dubbo.apache.org/)
