# dacp-jdbc
数据库连接池方面的学习Demo,包括BoneCP,Toncat Pool,Druid,C3P0,DBCP,Proxool

##测试各种数据库连接池的性能

```java
  Connection conn = dataSource.getConnection();
  conn.close();
```
test code https://github.com/alibaba/druid/blob/master/src/test/java/com/alibaba/druid/benckmark/pool/Case1.java
测试执行申请归还连接1,000,000（一百万）次总耗时性能对比。

##jar 包大小

|连接池名称 |Druid  |tomcat-jdbc  |DBCP |BoneCP |C3P0 |Proxool  |
|:--------|--------:|--------:|--------:|--------:|--------:|--------:|
|版本 |1.0.15 |9.0.0.M8 |1.4  |0.8  |0.9.1.2  |0.8.3  |
|大小 |1907kb |135kb  |157kb  |109kb  |597kb  |465kb  |

**阿里巴巴就是大**

##Java6 Environment

> OS	OS X 10.8.2
> CPU	intel i7 2GHz 4 core
> JVM	java version "1.6.0_37"

Java6 Benchmark Result

|Jdbc Connection Pool | 1 thread	| 2 threads	|5 threads	|10 threads	|20 threads	|50 threads|
| :-------- | --------:| --------:|--------:|--------:|--------:|--------:|
|Druid	|1,102	|1,509	|1,889	|1,904	|2,027|	1,977
|tomcat-jdbc|	1,399	|1,378	|2,257|	2,289|	2,305|	2,503
|DBCP	|3,144|	3,834|	6,276|	6,408	|6,563|	6,783
|BoneCP	|4,327	|3,598|	3,800|	5,242|	9,402	|19,066
|jboss-datasource|	4,912|	3,049	|6,868|	6,512	|40,146	|43,748
|C3P0	|18,570	|19,467	|15,270	|19,294	|28,195	|66,677
|Proxool|	16,221|	14,455|	24,688	|38,905|	48,087(Exception)	|58,238(Exception)

##Java7 Environment

> OS	OS X 10.8.2
> CPU	intel i7 2GHz 4 core
> JVM	java version "1.7.0_05"

Java7 Benchmark Result

|Jdbc Connection Pool |	1 thread	|2 threads|	5 threads	|10 threads	|20 threads|50 threads|
| :-------- | --------:| --------:|--------:|--------:|--------:|--------:|
|Druid		|898|1,191	|1,324	|1,362	|1,325	|1,459
|tomcat-jdbc|1,269	|1,378	|2,029	|2,103	|1,879	|2,025
|DBCP		|2,324	|5,055	|5,446	|5,471	|5,524	|5,415
|BoneCP		|3,738	|3,150	|3,194	|5,681	|11,018	|23,125
|jboss-datasource|4,377|2,988|3,680	|3,980	|32,708	|37,742
|C3P0		|10,841	|13,637	|10,682	|11,055	|14,497	|20,351
|Proxool	|16,337	|16,187	|18,310(Exception)	|25,945	|33,706(Exception)	|39,501 (Exception)

##结论
* Druid是性能最好的数据库连接池，tomcat-jdbc和druid性能接近。
* proxool在激烈并发时会抛异常，完全不靠谱。
* c3p0和proxool都相当慢，慢到影响sql执行效率的地步。
* bonecp性能并不优越，采用LinkedTransferQueue并没有能够获得性能提升。
* 除了bonecp，其他的在JDK 7上跑得比JDK 6上快
* jboss-datasource虽然稳定，但是性能很糟糕
