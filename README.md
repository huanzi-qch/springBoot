## 官网<br/> 
https://huanzi-qch.gitee.io/spring-boot <br/>

## 简介<br/> 
SpringBoot系列Demo代码<br/>

SpringBoot用的是2.1.0.RELEASE，Demo代码主要参照官方文档，以及百度、google写的，每一个子工程就是一个小案例，简单明了<br/>
这里的测试例子基本上跟博客的一样，没什么修改，大家看博客就好了<br/>

还是有很多人觉得这个项目太乱了，分不清子项目、父项目的关系，以及子项目直接的关系，我在这里简单描述一下<br/>

父项目是一个maven项目，继承spring-boot-starter-parent，同时引入了部分公用依赖<br/>
```xml
    <!--  父类继承spring-boot-starter-parent  -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.0.RELEASE</version>
        <relativePath/>
    </parent>

    <!-- 在父类引入一下通用的依赖 -->
    <dependencies>
        <!-- spring-boot-starter -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <!-- springboot web(MVC)-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- springboot -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!--lombok插件 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

        <!--热部署工具dev-tools-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
```
<br/>

每个子项目都是一个独立的SpringBoot项目，子项目直接继承父类<br/>
```xml
    <!--继承父类-->
    <parent>
        <groupId>cn.huanzi.qch</groupId>
        <artifactId>parent</artifactId>
        <version>1.0.0</version>
    </parent>
```
<br/>

每个子项目都是SpringBoot的一个知识点或者说技能点，具体见名思意！子项目相互之间并无关系，具体的知识点介绍都有对应的博客，详情请看下方的“前往博客查看详情”

## 仓库地址<br/> 
国外：https://github.com/huanzi-qch/springBoot<br/> 
国内：https://gitee.com/huanzi-qch/springBoot<br/> 

## 前往博客查看详情<br/> 
具体介绍请看我的博客[《SpringBoot系列》](https://www.cnblogs.com/huanzi-qch/category/1355280.html) <br/>

| 项目 | 博客 |
|  ----  | ----  |
| springboot-activiti7 | [SpringBoot系列——Activiti7工作流引擎](https://www.cnblogs.com/huanzi-qch/p/14758858.html) |
| springboot-admin-client、springboot-admin-server | [SpringBoot系列——admin服务监控](https://www.cnblogs.com/huanzi-qch/p/14894794.html) |
| springboot-aop | [SpringBoot系列——aop 面向切面](https://www.cnblogs.com/huanzi-qch/p/9916478.html) |
| springboot-async | [SpringBoot系列——@Async优雅的异步调用](https://www.cnblogs.com/huanzi-qch/p/11231041.html) |
| springboot-banner | [SpringBoot系列——花里胡哨的banner.txt](https://www.cnblogs.com/huanzi-qch/p/9916784.html) |
| springboot-cache | [SpringBoot系列——cache缓存](https://www.cnblogs.com/huanzi-qch/p/14871341.html) |
| springboot-cors | [SpringBoot系列——CORS(跨源资源共享)](https://www.cnblogs.com/huanzi-qch/p/11171734.html) |
| springboot-elasticsearch | [SpringBoot系列——ElasticSearch](https://www.cnblogs.com/huanzi-qch/p/11586205.html) |
| springboot-eventsandlisteners | [SpringBoot系列——事件发布与监听](https://www.cnblogs.com/huanzi-qch/p/14792984.html) |
| springboot-exceptionhandler | [SpringBoot系列——自定义统一异常处理](https://www.cnblogs.com/huanzi-qch/p/14788991.html) |
| springboot-filter | [SpringBoot系列——Filter 过滤器](https://www.cnblogs.com/huanzi-qch/p/11239167.html) |
| springboot-https | [SpringBoot系列——启用https](https://www.cnblogs.com/huanzi-qch/p/12133872.html) |
| springboot-i18n | [SpringBoot系列——i18n国际化](https://www.cnblogs.com/huanzi-qch/p/10000324.html) |
| springboot-idem | [SpringBoot系列——防重放与操作幂等](https://www.cnblogs.com/huanzi-qch/p/16118316.html) |
| springboot-jackson | [SpringBoot系列——Jackson序列化](https://www.cnblogs.com/huanzi-qch/p/11301453.html) |
| springboot-jar-war | [SpringBoot系列——快速构建项目](https://www.cnblogs.com/huanzi-qch/p/9946591.html) <br/> [SpringBoot系列——jar包与war包的部署](https://www.cnblogs.com/huanzi-qch/p/9948060.html) |
| springboot-jpa | [SpringBoot系列——Spring-Data-JPA](https://www.cnblogs.com/huanzi-qch/p/9970545.html ) <br/> [SpringBoot系列——Spring-Data-JPA（升级版）](https://www.cnblogs.com/huanzi-qch/p/9984261.html) <br/> [SpringBoot系列——Spring-Data-JPA（究极进化版） 自动生成单表基础增、删、改、查接口](https://www.cnblogs.com/huanzi-qch/p/10281773.html) |
| springboot-loadmyprofiles | [SpringBoot系列——加载自定义配置文件](https://www.cnblogs.com/huanzi-qch/p/11122107.html) |
| springboot-logback | [SpringBoot系列——Logback日志，输出到文件以及实时输出到web页面](https://www.cnblogs.com/huanzi-qch/p/11041300.html) |
| springboot-mail | [SpringBoot系列——mail](https://www.cnblogs.com/huanzi-qch/p/9957987.html) |
| springboot-mybatis-plus | [SpringBoot系列——MyBatis-Plus整合封装](https://www.cnblogs.com/huanzi-qch/p/13561164.html) |
| springboot-mybatis | [SpringBoot系列——MyBatis整合](https://www.cnblogs.com/huanzi-qch/p/10065136.html) |
| springboot-redis | [SpringBoot系列——Redis](https://www.cnblogs.com/huanzi-qch/p/10239888.html) |
| springboot-security | [SpringBoot系列——Security + Layui实现一套权限管理后台模板](https://www.cnblogs.com/huanzi-qch/p/11226705.html) <br/> [开源一套简单通用的后台管理系统](https://www.cnblogs.com/huanzi-qch/p/11534203.html) |
| springboot-swagger2 | [SpringBoot系列——Swagger2之Swagger UI，API实时接口文档](https://www.cnblogs.com/huanzi-qch/p/9964498.html) |
| springboot-thymeleaf | [SpringBoot系列——Thymeleaf模板](https://www.cnblogs.com/huanzi-qch/p/9930390.html) |
| springboot-timer | [SpringBoot系列——定时器](https://www.cnblogs.com/huanzi-qch/p/9916079.html) <br/> [SpringBoot系列——动态定时任务](https://www.cnblogs.com/huanzi-qch/p/15117482.html)|
| springboot-uniapp-mui | [SpringBoot系列——基于mui的H5套壳APP开发web框架](https://www.cnblogs.com/huanzi-qch/p/12727209.html) <br/> [SpringBoot系列——PC端、移动端页面适配方案](https://www.cnblogs.com/huanzi-qch/p/12053799.html) <br/> [移动端App uni-app + mui 开发记录](https://www.cnblogs.com/huanzi-qch/p/11972723.html) |
| springboot-validation | [SpringBoot系列——validation参数校验](https://www.cnblogs.com/huanzi-qch/p/14985530.html) |
| springboot-websocket | [SpringBoot系列——WebSocket](https://www.cnblogs.com/huanzi-qch/p/9952578.html) <br/> [WebSocket+Java 私聊、群聊实例](https://www.cnblogs.com/huanzi-qch/p/9889521.html) |

## [AD广告位](https://huanzi-qch.gitee.io/file-server/ad/adservice.html) （长期招租，如有需要请私信）<br/>
[【阿里云】阿里云最全的优惠活动聚集地！](https://www.aliyun.com/activity?userCode=ckkryd9h) <br/>
[【腾讯云】腾讯云当前最新优惠活动专区！](https://cloud.tencent.com/act/cps/redirect?redirect=11447&cps_key=e1c9db729edccd479fc902634492bf53) <br/>
<br/>

## QQ群<br/>
有事请加群，有问题进群大家一起交流！<br/>
![](https://huanzi-qch.gitee.io/file-server/images/qq.png) 

## 捐献<br/>
相应的资金支持能更好的持续项目的维护和开发，如果喜欢这个项目，请随意打赏！

| 支付宝 | 微信 |
|  ----  | ----  |
| <img src="http://huanzi-qch.gitee.io/file-server/images/zhifubao.png"  width="150"> | <img src="http://huanzi-qch.gitee.io/file-server/images/weixin.png" width="150"> |

## 学习资料<br/>
Spring全家桶的GitHub：https://github.com/spring-projects <br/>
SpringBoot官方文档：https://spring.io/projects/spring-boot <br/>
StringBoot官方GitHub：https://github.com/spring-projects/spring-boot <br/>
SpringBoot官方的简单引导案例：https://github.com/spring-projects/spring-boot/tree/v2.1.6.RELEASE/spring-boot-samples <br/>

这些资料有丰富的文档介绍、代码示例 <br/>
