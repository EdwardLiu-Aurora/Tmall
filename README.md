
#### 该项目为学习 how2j.cn 中 J2EE 天猫后台的代码和功能说明

![home](https://github.com/EdwardLiu-Aurora/tmall/blob/master/web/img/home.png?raw=true)

##### 项目介绍

1. 此项目为 how2j.cn 上的 J2EE 天猫后台模仿项目

2. 实现功能有前台的商品阅览、购买、评论等；后台的商品信息和用户管理等；

    1. 购物车 (立即购买、加入购物车、查看购物车页面、购物车页面操作)
    
    2. 订单状态流程 (生成订单 -> 确认支付 -> 后台发货 -> 确认收货 -> 评价)
    
    3. CRUD 各种实体类和对应表的映射增删查改
    
    4. 分页 (各种页面的分页浏览)
    
    5. 产品的属性管理 (产品和属性的一对多关系)
    
    6. 产品的图片维护 (产品的图片的一对多关系)
    
    7. 产品展示 (前台首页 产品页)
    
    8. 搜索查询 (没有使用搜索引擎技术，只是简单的数据库 select )
    
    9. 登录、注册、退出
    
    10. 登录验证 (使用前台 LoginFilter 来检查前台用户是否登录)

3. 开发的环境和代码和 how2j.cn 教程上的并不完全一致，因为本人根据自己的环境和实际的开发过程对代码进行了细微的调整。具体的调整和环境将会在后面说明。

4. 原版教程地址：http://how2j.cn/k/tmall-j2ee/tmall-j2ee-894/894.html?p=407 欢迎各位读者使用这个链接进行教程的购买。这也是对本人的支持。

5. 该项目没有使用框架，数据库使用 JDBC，页面使用 JSP，控制器使用 Servlet。使用的均为基础的 JAVA 后台开发技术。需要的基础有 JAVA 语言基础和 Servlet 基础以及 Tomcat 基础。 

##### 项目环境介绍

1. **IDE：**    IntelliJ IDEA 2018

2. **Java 版本：**   1.8.0_161

3. **服务器版本：**  Apache Tomcat/8.5.32

4. **MySQL 版本：**  8.0.11 MySQL Community Server - GPL

##### 项目目录介绍

1. 该目录下的 markdown (.md) 文件 记录了一些源文件的作用以及学习笔记

2. 该目录下的 tmall.sql 是恢复项目数据库的文件，请导入对应数据库恢复 mock 数据库进行项目的测试和学习

3. 部分代码进行了注释说明，本人会按部就班对代码功能进行注释

4. web/WEB-INF/lib/ 目录下保存了该项目使用的 .jar 库文件，请在学习时导入

##### 与原版教程的差异

1. 这里使用的 IDE 是 IntelliJ 而不是 Eclipse，具体的目录结构会有不同

2. 这里使用的 MySQL Connector 版本是更新的版本，与旧版不同

3. 因为使用了新版本的 MySQL ，项目中的 JDBC 连接部分子串加入了时区的设置字段

4. 因为使用了新版本的 MySQL ，项目中的 PrepareStatement 时，会加入附加的枚举类条件 Statement.RETURN_GENERATED_KEYS 来强制语句返回新增数据的 auto_increment ID 以供程序使用。如果删除，会出错

5. 原版教程中有部分拼写错误，已经全部修改。如 orignal 改为了 original

6. SQL 表中的 order 订单表的名称是 order_ 这并不是拼写错误，而是为了避免 order 表和SQL的 "order by" 中的 order 关键字重复冲突 

ps. 如果想了解更多项目的内容，或者跟着做这个项目，请使用如下分享链接，支持作者

##### http://how2j.cn/k/tmall-j2ee/tmall-j2ee-894/894.html?p=407	(JAVA WEB J2EE 练手项目，模仿天猫整站)
