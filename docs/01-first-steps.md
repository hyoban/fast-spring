# 第一步

从一个最简单的 Spring 示例来看。

```java
@RestController
public class Controller {

  @GetMapping("/")
  Map<String, String> root() {
    return Map.of("message", "Hello World");
  }
}
```

启动程序，访问对应地址，你将看到如下 JSON 响应，这是因为我们统一了返回的格式。
同样的，你也可以在交互式文档中进行接口测试。

```json
{
  "code": 200,
  "data": {
    "message": "Hello World"
  },
  "message": "调用成功",
  "timestamp": 1656296142904
}
```

如果你不熟悉 Spring 的依赖注入，或许会疑惑该将上面的代码置于何处。
事实上，你可以随意放置，只要处于 SpringApplication 的同级或更深层级的包下即可。
Spring 能够自动扫描你的代码，将其注册到该到的位置。

> 关于依赖注入，你可以阅读 [深入浅出 Spring 框架，原来以前白学了！ - 掘金](https://juejin.cn/post/7095532056632885284)

## 定义一个 controller

在 springboot 中我们使用注解来指定相关类，字段，方法所发挥的作用，给一个类加上 `@RestController` 注解来使得其成为 controller。
等效于加上 `@Controller` 和 `@ResponseBody` 两个注解。

而 `@Controller` 注解实际上被加上了 `@Component` 注解，这个注解的作用是让 springboot 能自动初始化类的实例到容器中，然后在合适的位置注入需要的实例。

`@ResponseBody` 注解的作用是让函数的返回值直接绑定到 web 响应的正文。
有别于单独使用 `@Controller` 注解时通常我们是指定返回的页面模板名，在前后端分离的时代，前端通过后端返回的 json 数据来加载界面。

关于 controller，实际上它是对于 java web 中的 servlet 进行了封装。
在我们的后端程序中，它的作用是接收发来的请求，访问对应的 service 以得到结果，返回回去。

也就是说，RestController 负责定义请求的路径，调用具体的处理方法，返回处理结果给客户端。

## 使用不同的 HTTP 方法

对函数冠以 `@RequestMapping` 注解，表示此函数处理特定访问场景下的请求。
通过填入参数来限定一些条件， 比如 path 指定请求访问的路径，method 指定请求所使用的方法。

为了方便，我们通常会使用对应特定 http 方法的注解，比如 `@GetMapping` 等价于 `@RequestMapping(method = RequestMethod.GET)`。
而 `@RequestMapping` 的作用通常是被置于类上，通过指定 path 来指定该类中方法所处理的请求的共同前缀。

这里的 HTTP 方法有下列之一：

- POST 创建数据
- GET 读取数据
- PUT 更新数据
- DELETE 删除数据

...以及更少见的几种：

- OPTIONS
- HEAD
- PATCH
- TRACE

## 返回内容

此前的准备中，我们已经统一了返回的格式和异常的捕获处理。
所以，你可以直接返回一个 Map，对象，字符串，或是直接抛出异常，都能够被很好的序列化为 JSON 格式返回。
