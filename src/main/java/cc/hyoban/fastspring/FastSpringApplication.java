package cc.hyoban.fastspring;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Log4j2
@SpringBootApplication
public class FastSpringApplication {

  public FastSpringApplication(Environment env) {
    this.env = env;
  }

  public static void main(String[] args) {
    SpringApplication.run(FastSpringApplication.class, args);

  }

  private final Environment env;

  @Bean
  public OpenAPI openAPI() {
    Info info = new Info()
      .title("Spring doc")
      .version("0.1.0");
    log.info("文档地址为: http://localhost:" + env.getProperty("server.port") + env.getProperty("springdoc.swagger-ui.path"));
    return new OpenAPI().info(info);
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
