# Cookie 和 Header

前面我们提到，Spring web 是基于 Java 的 Servlet 的封装，所以我们总是可以给函数注入 `HttpServletRequest` 和 `HttpServletResponse` 对象。
它们包含了我们需要进行的 Cookie 和 Header 的操作。

又或者，我们可以通过 `@CookieValue` 和 `@RequestHeader` 注解来获取某一特定的 Cookie 和 Header。
当需要给客户端设置内容时，可以返回 `ResponseEntity` 对象。
它代表了 HTTP 响应，允许我们设置状态码，Cookie，Header，Body 等。

```java
@GetMapping("/cookie-header")
public ResponseEntity<Object> cookieAndHeader(
  @CookieValue(
    value = "username",
    defaultValue = "Hyoban"
  ) String username,
  @RequestHeader HttpHeaders headers
) {
  HttpHeaders responseHeaders = new HttpHeaders();
  responseHeaders.set("my-header", "my-header-value");
  return ResponseEntity
    .ok()
    .headers(responseHeaders)
    .body(Map.of("username", username, "header", headers));
}
```

也就是说，大多数情况下，我们不需要与底层的 Servlet 打交道。

## 参考

- [How to use cookies in Spring Boot](https://attacomsian.com/blog/cookies-spring-boot)
- [How to Read HTTP Headers in Spring REST Controllers](https://www.baeldung.com/spring-rest-http-headers)
