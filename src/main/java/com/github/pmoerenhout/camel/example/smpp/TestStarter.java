package com.github.pmoerenhout.camel.example.smpp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestStarter implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private SmppTestService smppService;

  @Override
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    log.info("Spring context refreshed, now run tests in separate thread");
    // smppService.performAllThroughputTests();
    log.info("Spring context refreshed, now run tests in separate thread");
//    var ctx = event.getApplicationContext();
//    Arrays.stream(ctx.getBeanDefinitionNames()).forEach(b -> log.info("Bean {}", b));
  }
}
