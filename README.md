# WeChatSecurity

### 介绍

突发奇想想基于Url拦截实现一个简单的安全管理器，然后同时在微信小程序开发过程中难免会与微信的服务端API进行交互，所以在这个在这里面集成与微信服务端交互的功能。

### 说明

微信小程序端每次访问必须携带服务器发放的token。


PS：毕业开始打工转go了，看了看之前gitee上java的代码，想了想曾经也是javaboy，遂迁移至github。[**之前对于token跟cookie的概念没有理清楚，其实这里的token就是cookie，这个认证框架其实就是实现了一套session-cookie机制
，可以说与token没有撒子关系，服务端还是维持了一个cashe😓。真token还是jwt这种😄。还是基于servlet的，人家已经都有一套了，代码仅自娱🙂，仅供学习。**]
### API

~~~
    SecurityHelper.register("微信客户端发来的code");//注册用户
    SecurityHelper.logout();//注销用户
    SecurityHelper.getWeChat();//获取

    WeChatUser weChatUser = SecurityHelper.getWeChatUser();//获取当前用户
    weChatUser.getId();//获取微信用户Id
    weChatUser.getToken();//获取该用户token
    weChatUser.getRegisterTime();//获取注册时间
    weChatUser.getLastModifyTime();//获取最后查看时间
    weChatUser.setAttribute("name","jack");//可以设置(key,value)
    weChatUser.getAttribute("name");//获取之前设置的attribute

    //建议参考微信开发者文档API
    WeChat weChat = SecurityHelper.getWeChat();
    weChat.auth_code2Session("code");
    weChat.auth_getAccessToken();
~~~

#### spring注解支持
**注意**注解支持的类必须实现接口，本质是JDK原生的代理实现。
~~~
    //这个注解里能传递String数组
    //作用是此用户必须拥有数组中的所有角色
    @CheckRoles({"worker","boss"})
    public class UserServiceImpl implents UserService {

        //这个注解里能传递String数组
        //作用是此用户必须拥有数组中的至少一个角色
        @CheckRole({"worker","customer"})
        public void test(){
            System.out.println("test!!!")
        }
    }
~~~

### POM文件

~~~
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>we-chat-security</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>

    <dependencies>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.2.6.RELEASE</version>
            <scope>provided</scope>
        </dependency>

    </dependencies>
      </project>
~~~

****

### 使用

1. 
~~~    
    <filter>
        <filter-name>WeChatSecurity</filter-name>
        <filter-class>
        com.jack.weChatSecurity.core.WebHttpFilter
        </filter-class>
        <init-param>
            //选用json文件配置可以配置该参数
            //默认名为“wechatSecurity.json”
            //请在类路径下配置json文件(比如resources目录)
            <param-name>configFileName</param-name>
            <param-value>security.json</param-value>
            
            //选用类配置需要此参数
            <param-name>package</param-name>
            <param-value>com.mp</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>WeChatSecurity</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
~~~

2. 集成spring开启注解支持

~~~
    <bean class="com.jack.weChatSecurity.core.spring.WeChatSecurityAnnotationSupport">
    </bean>
~~~
@Bean注解注入也行。

***

### 配置

1. json文件

        {
        "weChatConfig": {
        "appId": "wxc51856e094e99c5d",
        "secret": "0a80f304a97eb22549e472032e67ebf6"
        },
        "securityConfig": {
        "sessionFileName": "session.db",//session存储文件名
        "sessionLocation": "C:\\Users\\JACK\\Desktop\\WechatSecurity",//session存储位置
        "savingTime": 100000,//session存储序列化间隔时间,
        "sessionCapacity": 2000,//session缓存的容量   
        "strategy":"LRU",//session缓存淘汰策略
        "ttlTime": 1000 //当且仅当策略为"TTL"时需要此属性，其他策略可以没有该属性 
        },
        "urlConfig": {
        "baseUrl":"",//可设置根路径
        "interceptUrl": [
        "(role)/api/user",//可配置角色
        "/api/version"
        ],             //拦截url
        "passUrl": [
        "/api/register"
        ]
        },             //放行url
        "datasourcePath": "com.daike.DIY" //数据路径
        }

2. 实现WeChatSecurityConfig接口并标记Config注解

        @Config
        public class MyConfig implements WeChatSecurityConfig {

            @Override
            public void setWeChatConfig(WeChatConfig weChatConfig) {
                weChatConfig.setSecret("456");
                weChatConfig.setAppId("123");
            }

            @Override
            public void setSecurityConfig(SecurityConfig securityConfig) {
                securityConfig.setSavingTime(1000000l);
                securityConfig.setSessionCapacity(1000);
                securityConfig.setSessionFileName("session.db");
                securityConfig.setSessionLocation("C:\\Users\\JACK\\Desktop\\WechatSecurity");
                securityConfig.setStrategy("FIFO");
            }

            @Override
            public void setUrlConfig(UrlConfig urlConfig) {
                List<String> in=new LinkedList<>();
                in.add("/api/user");
                urlConfig.setInterceptUrl(in);
            }

            @Override
            public Datasource getDataSource() {
                return new Diy();
            }
        }


### 更新记录

***
**Version: 1.0**

**Time: 2022.4.16**

基本功能的实现。
***
**Version: 1.1**

**Time: 2022.4.23**

之前url拦截是基于链表的全扫描来初步搭建的，鉴于如果需要拦截的url很多的话查询效率会降低，
改用类似文件目录或者说是字典树的一种数据结构(UrlTire),时间复杂度下降到与url层级正比的级别。
***
**Version: 1.2**

**Time: 2022.5.9**

引入role权限控制，重构之前直接在webfilter认证流程，采用认证链的模式，一层认证完交由下一层，
所有认证层都通过则放行。
***
**Version: 1.3**

**Time: 2022.5.21**

为了实现不依赖第三方jar包，实现了一个简单的json序列化工具。
***
**Version: 1.4**

**Time: 2022.6.6**

重构配置相关的代码，新增了注解类配置的方式
***
**Version: 1.5**

**2022.7.18**

删除了之前默认的session缓存机制，重构了session缓存机制并提供了LRU,LFU,TTL,FIFO,RANDOM五种session缓存淘汰策略。
***
**Version: 2.0**

**2022.7.27**

支持与spring集成来提供权限注解使用（**注解支持的类必须实现接口，本质是JDK原生的代理实现**）。@CheckRole，@CheckRoles。
***
**Version: 2.1**

**2022.9.24**

增加了WeChatUser的rememberMe功能并重构了一些代码。现在如果注册WechatUser后没有rememberMe的话，令牌有效期只持续这一次访问。
***