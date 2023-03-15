package my.example;

import lombok.RequiredArgsConstructor;
import my.example.config.ApplicationProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
@RequiredArgsConstructor
public class Application implements ApplicationRunner {

  private final MessageRequestFactory messageRequestFactory;
  private final SqsClient sqsClient;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(ApplicationArguments args) {
    printQueueUrls();
    sendMessage();
  }

  private void sendMessage() {
    final SendMessageRequest messageRequest = messageRequestFactory.createFrom(new Message("someone", 20));

    sqsClient.sendMessage(messageRequest);
  }

  private void printQueueUrls() {
    try {
      final ListQueuesRequest listQueuesRequest = ListQueuesRequest.builder().build();
      final ListQueuesResponse listQueuesResponse = sqsClient.listQueues(listQueuesRequest);

      for (String url : listQueuesResponse.queueUrls()) {
        System.out.println(url);
      }

    } catch (SqsException e) {
      System.err.println(e.awsErrorDetails().errorMessage());
      System.exit(1);
    }
  }
}
