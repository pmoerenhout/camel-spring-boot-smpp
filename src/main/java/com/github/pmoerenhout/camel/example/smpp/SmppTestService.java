package com.github.pmoerenhout.camel.example.smpp;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.github.pmoerenhout.camel.example.smpp.senders.CamelAsyncSender;
import com.github.pmoerenhout.camel.example.smpp.senders.ThreadedSender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmppTestService {

  private static final int NUMBER_OF_MESSAGES = 2500;

  @Autowired
  private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  @Autowired
  private CamelContext context;

  @Autowired
  private ThroughputService throughputService;

  @EndpointInject("smpp://{{camel.component.smpp.configuration.host}}:{{camel.component.smpp.configuration.port}}")
  private ProducerTemplate producerTemplate;

  @Bean
  CommandLineRunner init() {
    return args -> {
      performAllThroughputTests();
    };
  }

  public void performAllThroughputTests() {
    log.info("Perform all tests using endpoint {}", producerTemplate.getDefaultEndpoint());
    for (int i = 0; i < 10; i++) {
      throughputService.performThroughputTest(new CamelAsyncSender(context, producerTemplate, producerTemplate.getDefaultEndpoint()), NUMBER_OF_MESSAGES);
      throughputService.performThroughputTest(new ThreadedSender(threadPoolTaskExecutor, context, producerTemplate, producerTemplate.getDefaultEndpoint()),
          NUMBER_OF_MESSAGES);
    }
  }

}
