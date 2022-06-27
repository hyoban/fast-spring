package cc.hyoban.fastspring.fast.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {

  // 01 first-steps
  @GetMapping("/")
  Map<String, String> root() {
    return Map.of("message", "Hello World");
  }
}
