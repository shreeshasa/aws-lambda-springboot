package io.github.shreeshasa;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.shreeshasa.model.MessageDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class LambdaHandler implements RequestHandler<SQSEvent, Void> {

  @Cacheable (cacheNames = {"ctx"})
  public ApplicationContext getApplicationContext() {
    return SpringApplication.run(LambdaHandler.class);
  }

  @SneakyThrows
  @Override
  public Void handleRequest(SQSEvent event, Context context) {
    //    LOG.info("Handle request {}, {}, {}, {}", context, context.getFunctionName(), context.getInvokedFunctionArn(), context.getLogStreamName());
    //    // handler.proxyStream(inputStream, outputStream, context);
    //
    //    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    //    IOUtils.copy(inputStream, bos);
    //    String event = new String(bos.toByteArray(), LambdaContainerHandler.getContainerConfig().getDefaultContentCharset());
    //    LOG.info("Got event {}", event);
    ApplicationContext ctx = getApplicationContext();
    for (SQSEvent.SQSMessage msg : event.getRecords()) {
      log.info("{}", msg.getBody());
      ObjectMapper mapper = ctx.getBean(ObjectMapper.class);
      MessageDto messageDto = mapper.readValue(msg.getBody(), MessageDto.class);
      log.info("{}", messageDto);
    }
    return null;
  }
}