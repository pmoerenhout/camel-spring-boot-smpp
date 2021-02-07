package com.github.pmoerenhout.camel.example.smpp.senders;

import java.util.List;
import java.util.concurrent.Future;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;

import com.github.pmoerenhout.camel.example.smpp.Sender;

public class CamelAsyncSender implements Sender {

  private CamelContext camelContext;
  private ProducerTemplate producerTemplate;
  private Endpoint endpoint;

  public CamelAsyncSender(final CamelContext camelContext, final ProducerTemplate producerTemplate, final Endpoint endpoint) {
    this.camelContext = camelContext;
    this.producerTemplate = producerTemplate;
    this.endpoint = endpoint;
  }

  @Override
  public String getDescription() {
    return "Sender using Camel asyncSend()";
  }

  @Override
  public Future<Exchange> sendMessage(final String message, final String sourceAddress, final String destinationAddress) {
    var exchange = ExchangeBuilder.anExchange(camelContext)
        .withHeader("CamelSmppDestAddr", List.of(destinationAddress))
        //.withHeader("CamelSmppSourceAddr", sourceAddress)
        .withPattern(ExchangePattern.InOnly)
        .withBody(message).build();
    return producerTemplate.asyncSend(endpoint, exchange);
  }
}
