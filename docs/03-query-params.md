# 查询参数

```java
private final List<Map<String, String>> fakeItemsDb = List.of(
  Map.of("item_name", "Foo"),
  Map.of("item_name", "Bar"),
  Map.of("item_name", "Baz")
);

@GetMapping("/items")
public List<Map<String, String>> readItem(
  @RequestParam(defaultValue = "0") Integer skip,
  @RequestParam(defaultValue = "10") Integer limit
) {
  return fakeItemsDb.subList(skip, skip + limit > fakeItemsDb.size() ? fakeItemsDb.size() : limit);
}
```

使用 `@RequestParam` 注解来将函数参数指定为查询参数。
上面的例子中，可以匹配的 URL 为 http://127.0.0.1:8000/items/?skip=0&limit=10。

查询参数应当是可选的，指定 defaultValue 是一个好习惯。
如果你明确需要必填的查询参数，可以去除它，该注解默认配置 required 属性为 true。

前面一节中提到的通过指定参数类型来进行校验，同样也适用于此处。
得益于 Spring Web 对查询参数的自动转换，指定为 Boolean 类型的参数可以被自动转换。

```java
@RequestParam(defaultValue = "true") Boolean isShort
```

你可以声明任意数量的路径参数和查询参数，它们通过被冠以的注解来区别。

## 参数校验

给你的 controller 加上 `@Validated` 注解，然后就能使用相应的校验注解来限制参数。

```java
@RequestParam @Size(max = 5) String q
```

比如这个例子，使用 `@Size` 注解限制了字符串参数 q 的最大长度为 5。
更多校验相关可用的注解可以查看 `javax.validation.constraints` 包下的内容，
你只需要在代码导入部分，点击 constraints 位置跳转即可定位。
