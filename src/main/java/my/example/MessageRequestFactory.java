package my.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import my.example.config.ApplicationProperties;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest.Builder;

@Slf4j
@Component
public class MessageRequestFactory {

  private final String queueUrl;

  public MessageRequestFactory(ApplicationProperties properties) {
    this.queueUrl = properties.getAws().getSqsUrl();
  }

  public SendMessageRequest createFrom(Message message) {
    final ObjectMapper objectMapper = new ObjectMapper();
    final Builder builder = SendMessageRequest.builder()
        .queueUrl(queueUrl);

    try {
      builder.messageBody(objectMapper.writeValueAsString(message));
    } catch (JsonProcessingException e) {
      log.error("메시지 직렬화를 실패했습니다.", e);
    }

    return builder.build();
  }
}
