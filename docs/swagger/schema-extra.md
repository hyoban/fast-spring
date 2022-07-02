# 模式的额外信息

前面的教程中，得益于 springdoc 会扫描参数校验注解的信息，我们已经能够在文档中看到参数的一些相关信息。
但是如果希望将其作为接口文档的话，我们应该还需要手动补充一些信息。

## 接口信息

通过 `@Operation` 注解为接口添加额外信息。
主要的一般是 summary 和 description 选项，是为接口提供说明简短和详细的信息。

而如果你希望隐藏某个接口，让其不出现在文档中，则可以给接口加上 `@Hidden` 注解。

## 请求体信息

```java
record Item(
  @Schema(example = "Foo")
  @NotBlank
  String name,
  @Schema(example = "A very nice Item")
  String description,
  @Schema(example = "35.4")
  @NotNull
  Float price,
  @Schema(example = "3.2")
  Float tax
) {
}
```

你可以通过 `@Schema` 注解为请求体中的成员补充信息。
最常用的一般是给出一个示例或是简介。

## 接口参数信息

```java
@Parameter(
  description = "Query string for the items to search in the database that have a good match",
  deprecated = true,
  examples = {@ExampleObject(
    name = "foo",
    value = "2"
  ), @ExampleObject(
    name = "bar",
    value = "4"
  )}
)
```

你可以借助 `@Parameter` 注解来给 API 文档中的查询参数或是路径参数提供额外的信息。
比如对参数进行描述或者提供一个示例，又或是标记它为一个废弃的接口，再就是不将该参数信息暴露在文档中。

你也可以通过 examples 来提供多个示例，它接收一个 `@ExampleObject` 数组，你需要提供示例的名称和值。
