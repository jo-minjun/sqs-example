package my.example.config;

import java.net.URI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class SqsConfig {

  @Bean
  @Profile("local")
  public SqsClient localSqsClient() {
    return SqsClient.builder()
        .credentialsProvider(AnonymousCredentialsProvider.create())
        .endpointOverride(URI.create("http://localhost:4566"))
        .region(Region.AP_NORTHEAST_2)
        .build();
  }

  @Bean
  @Profile("!local")
  public SqsClient sqsClient() {
    return SqsClient.builder()
        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
        .region(Region.AP_NORTHEAST_2)
        .build();
  }
}
