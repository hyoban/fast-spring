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
你也可以设置自动编译 `File --> Settings --> Compiler --> Build Project automatically`

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

你可以参考作者在 [Issues 中的回复][7]

## 验证

默认情况下，Spring 并不会帮我们对 controller 中获取到的请求参数进行校验，需要通过注解手动配置。

你可以阅读 [使用 Spring Validation 优雅地校验参数][10] 来了解校验相关操作的详情。

> 如果有写的很完善的文章，本教程不会额外造轮子，只是给出原文链接。

## 统一返回结果

请阅读 [SpringBoot 如何统一后端返回格式？老鸟们都是这样玩的！][11]，然后结合前一节的异常处理。

需要注意，使用 `@RestControllerAdvice` 来统一返回结果之后，会影响到 swagger 文档的访问。
你需要设置 `basePackages` 属性，限定只扫描自己代码的包。

## 依赖注入

关于此部分，Spring 本身就已经做的很好了。我们将在后续的章节中展开介绍。

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
