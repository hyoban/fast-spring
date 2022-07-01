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

有可能你希望查询参数的命名为 kebab-case 风格，但这和 Java 变量的 PascalCase 不一致。
所以你需要通过该注解的 name 参数来给该查询参数提供别名。

## Bool 类型参数

前面一节中提到的通过指定参数类型来进行校验，同样也适用于此处。
得益于 Spring Web 对查询参数的自动转换，指定为 Boolean 类型的参数可以被自动转换。

```java
@RequestParam(defaultValue = "true") Boolean isShort
```

## 查询参数列表

你还可以将查询参数的类型设置为 List，这样你可以传递多个查询参数。

```java
@RequestParam(defaultValue = "foo,bar") List<String> q
```

比如上面可以匹配的地址为 `http://localhost:8000/items/?q=foo&q=bar`。
你同样可以为其制定默认值，以逗号分隔 List 中的每个值。
