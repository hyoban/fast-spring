package cc.hyoban.fastspring.fast;

import lombok.Data;

@Data
public class Result<T> {
  private int code;
  private T data;
  private String message;
  private long timestamp;

  public static <T> Result<T> of(
    int code,
    T data,
    String message
  ) {
    Result<T> result = new Result<>();
    result.setCode(code);
    result.setData(data);
    result.setMessage(message);
    return result;
  }

  public static <T> Result<T> success(
    int code,
    T data
  ) {
    Result<T> result = new Result<>();
    result.setCode(code);
    result.setData(data);
    result.setMessage("调用成功");
    return result;
  }

  public static <T> Result<T> fail(
    int code,
    String message
  ) {
    Result<T> result = new Result<>();
    result.setCode(code);
    result.setData(null);
    result.setMessage(message);
    return result;
  }
}
