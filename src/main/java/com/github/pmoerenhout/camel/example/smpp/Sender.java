package com.github.pmoerenhout.camel.example.smpp;

import java.util.concurrent.Future;

import org.apache.camel.Exchange;

public interface Sender {

  String getDescription();

  Future<Exchange> sendMessage(String text, String source, String destination);
}
