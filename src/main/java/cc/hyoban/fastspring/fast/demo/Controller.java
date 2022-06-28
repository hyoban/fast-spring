package cc.hyoban.fastspring.fast.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

enum ModelName {
  alexnet,
  resnet,
  lenet,
}

@RestController
public class Controller {

  // 01 first-steps
  @GetMapping("/")
  Map<String, String> root() {
    return Map.of("message", "Hello World");
  }

  // 02 path-params
  @GetMapping("/items/{itemId}")
  public Map<String, Object> readItem(@PathVariable Integer itemId) {
    return Map.of("item_id", itemId);
  }

  @GetMapping("/models/{modelName}")
  public Map<String, Object> getModel(@PathVariable ModelName modelName) {
    return Map.of("model_name", modelName.name());
  }

}
