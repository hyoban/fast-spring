package cc.hyoban.fastspring;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FastSpringApplication {

  public static void main(String[] args) {
    SpringApplication.run(FastSpringApplication.class, args);
  }

  @Bean
  public OpenAPI openAPI() {
    Info info = new Info()
      .title("Spring doc")
      .version("0.1.0");
    return new OpenAPI().info(info);
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
