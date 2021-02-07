package com.github.pmoerenhout.camel.example.smpp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ThroughputService {

  @Value("${smpp.message}")
  private String message;

  private List<Future<Exchange>> responses;

  public void performThroughputTest(final Sender sender, final int numberOfMessages) {
    log.info("{} with {} messages", sender.getDescription(), numberOfMessages);
    final AtomicInteger okResponses = new AtomicInteger(0);
    responses = new ArrayList<>(numberOfMessages);
    var start = System.currentTimeMillis();
    IntStream.range(0, numberOfMessages).forEach(i -> {
      responses.add(sender.sendMessage(String.format(message, i), "1234", "31612345678"));
    });
    var send = System.currentTimeMillis();
    responses.forEach(f -> {
      try {
        var exchange = f.get(60, TimeUnit.SECONDS);
        if (exchange.getException() == null) {
          var m = f.get().getMessage();
          // log.info("SMPP response: {} for message '{}'", m.getHeader(SmppConstants.ID), m.getBody());
          okResponses.incrementAndGet();
        } else {
          log.error("Could not send message", exchange.getException());
        }
      } catch (InterruptedException | ExecutionException | TimeoutException e) {
        log.error("Exception", e);
      }
    });
    var finish = System.currentTimeMillis();
    log.info("Send {} messages in {} milliseconds", responses.size(), send - start);
    log.info("Got {} responses in {} milliseconds", okResponses, finish - start);
    log.info("Throughput is {} msg / second", responses.size() * 1000 / (finish - start));
  }

}
