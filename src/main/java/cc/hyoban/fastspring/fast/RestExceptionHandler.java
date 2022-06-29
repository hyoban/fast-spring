package cc.hyoban.fastspring.fast;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

  private static final String BAD_REQUEST_MSG = "客户端请求参数错误";

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Result<String> exception(Exception e) {
    return Result.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
  }

  // <1> 处理 form data方式调用接口校验失败抛出的异常
  @ExceptionHandler(BindException.class)
  public Result bindExceptionHandler(BindException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    List<String> collect = fieldErrors.stream()
      .map(o -> o.getDefaultMessage())
      .collect(Collectors.toList());
    return Result.of(HttpStatus.BAD_REQUEST.value(), collect, BAD_REQUEST_MSG);
  }

  // <2> 处理 json 请求体调用接口校验失败抛出的异常
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    List<String> collect = fieldErrors.stream()
      .map(o -> o.getDefaultMessage())
      .collect(Collectors.toList());
    return Result.of(HttpStatus.BAD_REQUEST.value(), collect, BAD_REQUEST_MSG);
  }

  // <3> 处理单个参数校验失败抛出的异常
  @ExceptionHandler(ConstraintViolationException.class)
  public Result constraintViolationExceptionHandler(ConstraintViolationException e) {
    Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
    List<String> collect = constraintViolations.stream()
      .map(o -> o.getMessage())
      .collect(Collectors.toList());
    return Result.of(HttpStatus.BAD_REQUEST.value(), collect, BAD_REQUEST_MSG);
  }

}