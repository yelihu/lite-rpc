# CAP理论和它的朋友们

## CAP基本解释

### CAP理论是什么？

> *The CAP Theorem states that, in a distributed system (a collection of interconnected nodes that share data.), you can
only have two out of the following three guarantees across a write/read pair: Consistency, Availability, and Partition
Tolerance - one of them must be sacrificed.*
>
> *—— Robert Greiner Robert*

也就是一致性、可用性和分区容错性只能保证”三者其中一个“。

CAP理论讨论的分布式系统的特点是什么？*interconnected和share data* 也就是互联且互相数据复制的。

CAP理论讨论的是分布式系统的那些活动？数据的读写活动

### CAP理论当中三个性

| 性质                  | 释义                                                                                                                                                        |
|---------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| Consistency         | 站在客户端的角度来观察系统的行为和特征来看，对某个指定的客户端来说，读操作保证能够返回最新的写操作结果。这就是保证了Consistency。A **<u>read</u>** is guaranteed to return the most recent write for a given client. |
| Availability        | 非故障的节点在合理的时间内返回合理的响应（不是错误和超时的响应）。A non-failing node will return a reasonable response within a reasonable amount of time (no error or timeout).           |
| Partition Tolerance | 当出现网络分区后，系统能够继续“履行职责”。The system will continue to function when network partitions occur.                                                                 |

思考：

1. 针对Consistency来说，如果其解释为”All nodes see the same data at the same time.“是合理的吗？
2. 对于Availability来说，解释为”Every request gets a response on success/failure.“是合理的吗？
3. 对于Partition Tolerance来说，解释为”System continues to work despite message loss or partial failure.“是合理的吗？

### 为什么只有CP、AP

因为分布式环境下无论如何无法保证100%的网络可靠性。

举个例子：如果舍弃Partition
Tolerance，也就是先选择Consistency和Availability，两台机器一台库存3、一台库存5，当网络分区的时候因为保证Consistency所以在两边同步数据之前是要拒绝服务的，这就和Availability发生了冲突（
*想要保证Availability必须要保证response是NO ERROR和NO TIMEOUT的*）。

## CAP细节掌握

> 本章主要将CAP、ACID、BASE几个概念的关键区别点

### CAP的一些细节

**CAP 关注的是数据，粒度较小，不是整个系统**

没有系统是只能选择CP/AP的（电商也会包含支付系统、库存系统和商品详情系统等多种要么选择CP，要么选择AP的情况），从整个系统的角度去选择CP还是AP，就会发现顾此失彼。

给我们的启示就是：需要将系统内的数据按照不同的应用场景和要求进行分类，每类数据选择不同的策略（CP还是AP），而不是直接限定整个系统所有数据都是同一策略。

**CAP忽略网络延迟**

因为网络延迟的存在，C不是真正意义上的实现的，选择”CP“策略的系统里面总会有短暂的一小段时间是达成不了数据一致性的。这种情况发生在多节点之间的数据复制，网络延迟造成的这个短暂时间无法达成C，会使得分布式的多点写入成为不可能事件。

意味着类似”单用户余额、单商品库存“这类场景无法实现分布式的多点写入，解决方案一般是将数据进行sharding，分布到不同的节点上。如果出现某节点down了也只是造成了一部分的不可用。

**正常运行的时候，是可以满足CA的**

上面讨论的”为什么只有CP、AP“其实是在Partition出现的时候，这个时候才需要做取舍，对于架构设计来说<u>
既要考虑分区发生时选择CP还是AP，也要考虑分区没有发生时如何保证CA</u>。比如选择消息队列、数据库同步的方式保证C & A的实现。

**P发生之后，我们能做什么？**

如果系统未来面临分区的情况，预先做了什么准备可以帮助系统在分区情况修复之后快速恢复到CA的状态。比如某节点和其他节点断联，这个时候考虑将请求记录在日志当中，当恢复之后快速的和各个节点交换日志进行同步的操作。

### ACID

ACID也会出现Consistency，这里的Consistency和CAP理论的Consistency是一个意思吗？对于ACID来说，Consistency是保证**事务开始前后
**
，数据库不会出现数据不一致的情况。假如一个事务要求是”A账户转账B账户500元“，保证Consistency的结果就是：不会出现”事务结束之后，A少了500元，但是B增加的不是500元“的情况。如果事务破坏了数据的完整性和约束条件，那么说明这个事务实现没有达到ACID当中的C。

> 什么是"破坏了数据的完整性和约束条件"？完整性就是数据没有篡改（本该给500元给B账户的结果给了0元），约束条件就是主键、唯一性和检查约束（年龄必须≥0）。

对于CAP理论来说，C是分布式系统数据读写的一致，<u>ACID的C和CAP的C关注的对象是不同的</u>。

### BASE

BASE理论是对CAP的延伸和补充（<u>对CAP中AP方案的一个补充</u>），核心思想是即使无法做到强一致性（CAP的一致性就是强一致性），但应用可以采用适合的方式达到最终一致性。

|                             | 释义                                              |
|-----------------------------|-------------------------------------------------|
| **B**asically **A**vailable | 允许损失部分可用性，即保证核心可用，需要识别那些是最影响业务的服务，保证这些核心服务的可用性。 |
| **S**oft State              | 允许系统出现数据不一致的状态（AKA Soft State）                  |
| **E**ventual Consistency    | 系统中的所有数据副本经过一定时间后，最终能够达到一致的状态。                  |

前面讲”CAP的一些细节“的时候讲到了”CAP忽略网络延迟“，因为这个延迟的存在，CP是无法实现的，那么这里的E，其实就讲述的是类似的说法，因为完美的一致性无法实现，所以CP里面的C，一般实现出来的效果就是最终一致性。

讲述AP的时候，其实也不意味着放弃一致性，当分区出现并恢复之后，系统应该要实现最终一致性。

### 总结

ACID是数据库事务完整性的理论，和本章节讲的分布式系统设计理论没有什么关系，注意ACID当中C是讲述事务前后的数据库的一致性的（数据完整、不破坏约束）。

CAP是分布式系统设计理论，当P发生时，CA是无法满足的，但在架构设计的时候，我们的职责是在P没发生的时候，尽可能设计满足CA的系统，并且在P发生之后，具有快速恢复CA状态的系统。

BASE是CAP理论中AP方案的延伸，强调的是最终一致性，在对CAP当中的CP无法实现完全的C有一定的补充。

## 参考资料

1. [李运华-从0开始学架构]()
2. [CAP Theorem: Revisited  *by Robert Greiner Robert*](https://robertgreiner.com/cap-theorem-revisited/)
3. [CAP 理论十二年回顾："规则"变了 by Eric Brewer](https://www.infoq.cn/article/cap-twelve-years-later-how-the-rules-have-changed/)
4. [左耳朵耗子推荐：分布式系统架构经典资料](https://www.infoq.cn/article/2018/05/distributed-system-architecture)
5. [Notes on Distributed Systems for Young Bloods](https://www.somethingsimilar.com/2013/01/14/notes-on-distributed-systems-for-young-bloods/)
6. [A Note on Distributed Computing](https://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.41.7628)
7. [Distributed systems for fun and profit](https://book.mixu.net/distsys/)
8. [Distributed systems theory for the distributed systems engineer](https://www.the-paper-trail.org/post/2014-08-09-distributed-systems-theory-for-the-distributed-systems-engineer/)





