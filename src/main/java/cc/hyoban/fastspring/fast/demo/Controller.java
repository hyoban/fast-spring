package cc.hyoban.fastspring.fast.demo;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

enum ModelName {
  alexnet,
  resnet,
  lenet,
}

record Item(
  @NotBlank
  String name,
  String description,
  @NotNull
  Float price,
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
  public Map<String, Object> getModel(@PathVariable ModelName modelName) {
    return Map.of("model_name", modelName.name());
  }

  // 03 query-params
  @GetMapping("/items")
  public List<Map<String, String>> readItem(
    @RequestParam(defaultValue = "0") Integer skip,
    @RequestParam(defaultValue = "10") Integer limit
  ) {
    return fakeItemsDb.subList(skip, skip + limit > fakeItemsDb.size() ? fakeItemsDb.size() : limit);
  }

  @PostMapping("/items/")
  public Item createItem(@Validated @RequestBody Item item) {
    return item;
  }

}
