package cc.hyoban.fastspring.fast.demo;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum ModelName {
  alexnet,
  resnet,
  lenet,
}

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

@RestController
public class Controller {

  private final List<Map<String, String>> fakeItemsDb = List.of(
    Map.of("item_name", "Foo"),
    Map.of("item_name", "Bar"),
    Map.of("item_name", "Baz")
  );

  // 01 first-steps
  @GetMapping("/")
  Map<String, String> root() {
    return Map.of("message", "Hello World");
  }

  // 02 path-params
  @GetMapping("/items/{itemId}")
  public Map<String, Object> readItem(
    @PathVariable Integer itemId,
    @RequestParam(defaultValue = "true") Boolean isShort
  ) {
    return Map.of("item_id", itemId, "is_short", isShort);
  }

  @GetMapping("/models/{modelName}")
  public Map<String, Object> getModel(
    @PathVariable ModelName modelName
  ) {
    return Map.of("model_name", modelName.name());
  }

  // 03 query-params
  @GetMapping("/items")
  public Map<String, Object> readItems(
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
    @RequestParam(
      name = "skip",
      defaultValue = "0"
    )
    Integer skip,
    @RequestParam(defaultValue = "10") Integer limit,
    @RequestParam(defaultValue = "foo,bar") List<String> q
  ) {
    Map<String, Object> result = new HashMap<>();
    result.put("items", fakeItemsDb.subList(skip, skip + limit > fakeItemsDb.size() ? fakeItemsDb.size() : limit));
    result.put("q", q);
    return result;
  }

  // 04 body
  @PostMapping("/items/")
  public Item createItem(
    @Validated
    @RequestBody
    Item item
  ) {
    return item;
  }

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
}
