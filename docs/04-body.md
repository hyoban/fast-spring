# 请求体

> 当你需要将数据从客户端（例如浏览器）发送给 API 时，你将其作为「请求体」发送。
> 请求体是客户端发送给 API 的数据。响应体是 API 发送给客户端的数据。
> 你的 API 几乎总是要发送响应体。但是客户端并不总是需要发送请求体。
>
> 你不能使用 GET 操作（HTTP 方法）发送请求体。
> 要发送数据，你必须使用下列方法之一：POST（较常见）、PUT、DELETE 或 PATCH。
>
> 来自 FastAPI 文档

首先定义表示数据模型的类，这里我们可以使用 record。
校验注解也是适用的。

```java
record Item(
  @NotBlank
  String name,
  String description,
  @NotNull
  Float price,
  Float tax
) {
}
```

例如，上面的模型声明了一个这样的 JSON「object」：

```json
{
  "name": "Foo",
  "description": "An optional description",
  "price": 45.2,
  "tax": 3.5
}
```

由于 description 和 tax 是可选的，下面的 JSON「object」也将是有效的：

```json
{
  "name": "Foo",
  "price": 45.2
}
```

使用 `@RequestBody` 将函数参数指定为从请求体中获取。

```java
@PostMapping("/items/")
public Item createItem(@Validated @RequestBody Item item) {
  return item;
}
```

你可以同时声明路径参数，查询参数，请求体等，只需要冠以不同的注解即可。

请注意，你只能将一个函数参数加上 RequestBody 的注解，如果你有多个类共同组成的 json 结构，
请创建一个新的包装类来消费请求体的内容。

> 关于不支持的原因，请参考 [Spring MVC controller with multiple @RequestBody](https://stackoverflow.com/a/18690904)
