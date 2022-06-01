package cc.hyoban.fastspring.fast;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.example.demo")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

  private final ObjectMapper objectMapper;

  @Override
  public boolean supports(
    MethodParameter returnType,
    Class<? extends HttpMessageConverter<?>> converterType
  ) {
    return true;
  }

  @SneakyThrows
  @Override
  public Object beforeBodyWrite(
    Object body,
    MethodParameter returnType,
    MediaType selectedContentType,
    Class<? extends HttpMessageConverter<?>> selectedConverterType,
    ServerHttpRequest request,
    ServerHttpResponse response
  ) {

    if (body instanceof String) {
      return objectMapper.writeValueAsString(
        Result.success(HttpStatus.OK.value(), body)
      );
    }

    if (body instanceof Result<?>) {
      return body;
    }

    return Result.success(HttpStatus.OK.value(), body);
  }
}