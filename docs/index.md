# 欢迎

这里是我尝试写的关于 [Spring][1] 的教程。
由于非常中意 [FastAPI][2] 的使用体验，所以我尝试在 Spring 上复刻它。
如你所见，这份教程也是同样使用 [MkDocs][3] 构建的。

在本章节中，将从如何复刻 FastAPI 的基础开发体验讲起，后续的文章将介绍 Spring 中 FastAPI 的对应写法。
本章节最后的部分，我将给出初始化 Spring 项目的模板。所以你可以直接使用该模板来往后阅读。

阅读完本教程之后，你将会具有基础的 web 开发能力。

## 初始化项目

你可以使用 [Spring initializr][8] 来初始化一个 Spring 的项目。
我为你默认添加了如下依赖：

1. Lombok 如果你希望手动创建 getter setter 等方法可以去除它。
2. Spring Boot DevTools 它可以让我们在修改完代码之后不必完全重启程序。
3. Spring Web 教程介绍的重头戏。

### DevTools 自动重启原理

Spring Boot DevTools 与你的编辑器无关。并且它能识别程序的运行环境，如果是生产环境则会自动禁用。

它将程序加载到 JVM 上两个独立的 class loader，一个用于加载我们的代码和配置文件，另一个用于加载我们的依赖库。
前者易于改变，而后者基本保持不变。
当触发改变时，包含我们代码的 class loader 和 spring application context 会重启，
但是 JVM 实例和另一个 class loader 会保持不变。
所以当我们修改 pom.xml 文件的依赖项后，需要手动重启程序。

在 IDEA 中，触发重启的方式是 Build 项目，这样能更新类路径。

### 创建配置文件

修改 Spring Boot 的配置文件格式为 yml，然后加入如下内容：

```yaml
server:
  port: 8888 # 修改默认端口，防止冲突
```

## 自动生成文档

请在你的项目添加 [springdoc-openapi-ui][4] 的依赖，它能够帮我们自动生成可交互的 API 文档。
然后添加配置文件：

```yaml
springdoc:
  swagger-ui:
    path: /docs # 使用和 FastAPI 一致的文档链接
    show-extensions: true # 支持校验相关的注解
    show-common-extensions: true
```

补充文档的一些相关信息，请将如下代码添加到某个被标记了 `@Configuration` 注解的类下。
更多可补充的信息请参考文档。

```java
@Bean
public OpenAPI openAPI() {
Info info = new Info()
    .title("Spring doc")
    .version("0.1.0");
return new OpenAPI().info(info);
}
```

项目启动后访问 [http://localhost:8888/docs][9] 地址即可看到生成的文档。

> 你可以查阅 [新手工程師的程式教室][5] 的一篇 [相关教程][6] 来了解基础用法。

### 支持 JSR-303

在 swagger 文档中，校验 bean 的注解得到了支持。
如果我们输入了不符合要求的参数，它会弹框提示，而非显示服务器返回的错误信息。
而如果希望显示这些校验注解的信息，需要在配置文件中开启插件。

> 你可以参考作者在 [Issues 中的回复][7]

## 参数校验

默认情况下，Spring 并不会帮我们对 controller 中获取到的请求参数进行校验，需要通过注解手动配置。

你可以阅读 [使用 Spring Validation 优雅地校验参数][10] 来了解校验相关操作的详情。

更多校验相关可用的注解可以查看 `javax.validation.constraints` 包下的内容，
你只需要在代码导入部分，点击 constraints 位置跳转即可定位。

> 如果有写的很完善的文章，本教程不会额外造轮子，只是给出原文链接。
>
> 本教程将不会在后面介绍校验相关的内容，Spring 的注解参数校验比 FastAPI 高到不知道哪里去了。

## 统一返回结果

请阅读 [SpringBoot 如何统一后端返回格式？老鸟们都是这样玩的！][11]，然后结合前一节的异常处理。

需要注意，使用 `@RestControllerAdvice` 来统一返回结果之后，会影响到 swagger 文档的访问。
你需要设置 `basePackages` 属性，限定只扫描自己代码的包。

## 依赖注入

关于此部分，Spring 本身就已经做的很好了。我们将在后续的章节中展开介绍。

## 前置概念

1. container / spring application context 用于创建和管理应用组件
   1. 在容器基础上 spring 和一些其他的库提供了各种现代程序开发所需要的功能
2. components / beans 被组装到一起来构建一个完整的应用程序
   1. xml configuration
   2. java-based configuration class
      1. `@Configuration` 告诉 spring 这个类是用于提供组件到容器
      2. `@Bean` 标记方法，表示该方法返回的类应该作为组件被添加到容器
         1. 默认情况下，bean 的 id 和方法名相同
      3. 相比 xml 的配置方式，注解的方式有着更好的类型安全和可重构性
      4. 大部分时间不需要手动来知名配置，sping 可以自动配置
3. dependency injection 由容器来创建和维护所有的组件，然后将组件注入到需要它们的地方
   1. constructor argyments
   2. property accessor methods
4. automatic configuration
   1. component scanning 从应用的 classpath 自动发现组件并创建到容器
   2. autowiring 自动注入组件到其他需要它的组件的位置
   3. spring boot 根据你的 classpath，环境变量，和其他因素来进行自动的配置和组装
      1. 大量减少了显示的配置，不管是 xml 还是 java 注解
      2. 尽可能的使用自动配置，并且抛弃 xml 的配置方式

## 文件结构

1. 典型的 maven 或者 gradle 项目，包含源码，测试代码，和资源目录
2. maven 相关
   1. `mvnw` 和 `mvnw.cmd` 是 maven wrapper 的脚本，可以让你无需提前安装 maven
   2. `pom.xml` maven 构建的具体细节
      1. `<parent>` 定义依赖的父 pom 文件，通过指定 `spring-boot-starter-parent` 的版本，我们可以将其他相关依赖的版本交由它来确定
      2. `<dependencies>` 定义我们需要使用的依赖
         1. starter 结尾的依赖项 id，表明它本身不包含实际的代码，只是组合其他的库
            1. 你的依赖项会减少很多
            2. 语义化的确定依赖能给我们带来的功能
            3. 你不必手动去指定各个依赖的版本，spring boot 会帮你确定各个合适的版本
      3. `<plugins>` 构建的插件，spring boot 插件提供了很多功能
         1. 使用 maven 来启动程序
         2. 确保所有的依赖库会被打包到 JAR 文件，以及在运行时处于 classpath 下
         3. 在 manifest 文件中指定了项目启动的主类
3. spring 相关
   1. `application.properties` 指定项目配置
   2. `static` `templates`
   3. 程序启动
      1. `@SpringBootApplication`
         1. `@SpringBootConfiguration` 一个特定形式的 `@Configuration` 注解
            1. 你可以添加一两个简单的配置到启动类下
            2. 创建更多的配置类来提供更多的不能自动配置的的东西
         2. `@EnableAutoConfiguration` 启用 spring boot 的自动配置功能
         3. `@ComponentScan` 启动组件扫描，这使得 spring 能够自动发现被标记为组件的类，并且注册到容器
      2. `main()`
         1. 在 JAR 文件被执行时运行此方法，样板代码
         2. `run()` 指定真正的组装程序的代码，创建 spring application context
         3. 传入的配置类参数不必和启动类相同，但这是最常见和方便的选择
         4. 传入命令行参数到该方法
   4. 程序测试
      1. 直接打包运行来测试
         1. `./mvnw package`
         2. `java -jar target/taco-cloud-0.0.1-SNAPSHOT.jar`
         3. `./mvnw spring-boot:run`
      2. 编写测试代码
         1. 即使测试代码为空，我们也能通过运行测试代码来判断程序是否能够争取执行
         2. `@SpringBootTest` 告诉 JUnit 来使用 spring boot 的能力来初始化测试
            1. `@ExtendWith(SpringExtension.class)` 添加 spring 测试的能力到 JUnit 5
            2. 可以理解为执行了 `SpringApplication.run()`
         3. `./mvnw test`

## 构建和运行程序

1. Tomcat 是 spring boot 程序的一部分，而无需将代码部署到 Tomcat
2. spring boot 的自动配置库，监测到我们使用的依赖项，然后使得 spring application context 来启用和配置对应的功能
3. Spring Boot DevTools
   1. 不是一个 IDE 工具
   2. 开发时启用，生产环境下自动禁用
   3. 自动禁用模板缓存
      1. 模板文件默认会被程序缓存，这会导致修改模板文件无法在浏览器中的到更新
      2. 如果你希望变化时自动刷新浏览器，就需要额外安装浏览器插件
   4. h2 数据库终端
      1. http://localhost:8080/h2-console
      2. 好像没啥用，IDEA 的功能更强

## spring 生态介绍

1. core spring
   1. spring 宇宙的基础，提供 container 和 di 的框架
   2. spring MVC，REST APIs
   3. 基础的数据库支持 JdbcTemplate
   4. reactive program, Spring WebFlux
2. Spring Boot
   1. starter dependencies，依赖管理
   2. autoconfiguration
   3. Actuator 监控运行时程序的内部状态
   4. 灵活地指定环境配置
   5. 在核型库上额外的测试支持
   6. Spring boot cli 使用 groovy 脚本来编写程序
3. Spring Data
   1. 使用 Java 接口来定义数据模型
   2. 使用语义化的函数来进行 CRUD 的操作
   3. 支持包括关系型，文档型，graph 等类型的数据库
4. Spring Security
   1. 鉴别，鉴权，API 安全
5. Spring Integration 和 Spring Batch
   1. 整合其他程序
   2. 前者处理实时的整合，后者按照批次和触发器来进行整合处理
6. Spring Cloud
   1. 微服务，即组合多个单独部署的程序
   2. [Cloud Native Spring in Action](http://www.manning.com/books/cloud-native-spring-in-action)
7. Spring Native
   1. 将 Spring Boot 项目编译程序可执行文件
   2. GraalVM native-image compiler
   3. 非常快的启动和很小的占用空间

[1]: https://spring.io/
[2]: https://fastapi.tiangolo.com/
[3]: https://squidfunk.github.io/mkdocs-material/getting-started/
[4]: https://springdoc.org/#getting-started
[5]: https://chikuwa-tech-study.blogspot.com
[6]: https://chikuwa-tech-study.blogspot.com/2021/07/spring-boot-swagger-openapi-documentation.html
[7]: https://github.com/springdoc/springdoc-openapi/issues/1677
[8]: https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.7.0&packaging=jar&jvmVersion=17&groupId=com.example&artifactId=demo&name=demo&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.demo&dependencies=devtools,lombok,web,validation
[9]: http://localhost:8888/docs
[10]: https://segmentfault.com/a/1190000023451809
[11]: https://juejin.cn/post/6986800656950493214
