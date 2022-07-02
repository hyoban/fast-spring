# 路径参数

## 基础使用

```java
@GetMapping("/items/{itemId}")
public Map<String, String> readItem(@PathVariable String itemId) {
  return Map.of("item_id", itemId);
}
```

在路径中使用花括号表示路径参数的占位，然后在方法上声明对应的参数，当然可以使用 IDEA 的提示自动生成。

你可随意指定同样能匹配该路由的静态规则，Spring 会自动优先匹配。
FastAPI 的路径操作是按照顺序来的。

> 这里的路径参数不能为路径字符串，请使用查询参数，或者是结合正则从 request 中提取。
>
> 参考 [Spring 3 RequestMapping: Get path value](https://stackoverflow.com/q/3686808)

## 参数校验

你可以通过将 String 改为 Integer 或是其他类型来进行类型校验。
在修改完类型之后，我们可以看到返回的 JSON 数据中，item_id 为数字而非字符串。
此后，如果你传入不对应的路径参数类型，就会看到一个清晰的报错。

`@Validated` 和校验相关的注解并不适用于路径参数，你可以使用正则表达式等其他方式来进行校验。

> 请参考 [How to validate Spring MVC @PathVariable values?](https://stackoverflow.com/q/19419234) 中校验的方法。
>
> 此外，里面有一个回答指出，路径参数应该始终不被校验，你需要依据其展示给用户可阅读的信息。

## 预设值

你可以将类型指定为一个枚举类型，以设定可选的预置选项。
这些信息在交互式的文档中同样可以展示。

```java
enum ModelName {
  alexnet,
  resnet,
  lenet,
}

@GetMapping("/models/{modelName}")
public Map<String, Object> getModel(@PathVariable ModelName modelName) {
  return Map.of("model_name", modelName.name());
}
```
