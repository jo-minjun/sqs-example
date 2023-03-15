package my.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

  private final Aws aws = new Aws();

  @Getter
  @Setter
  public static class Aws {

    private String sqsUrl;
  }
}
