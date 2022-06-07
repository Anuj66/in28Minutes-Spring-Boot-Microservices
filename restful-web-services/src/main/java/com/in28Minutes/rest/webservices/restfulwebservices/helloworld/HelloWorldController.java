package com.in28Minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

  @Autowired private MessageSource messageSource;

  @GetMapping("/hello-world")
  public String helloWorld() {
    return "Hello World!";
  }

  @GetMapping("/hello-world-bean")
  public HelloWorldBean helloWorldBean() {
    return new HelloWorldBean("Hello World");
  }

  @GetMapping("/hello-world-internationalized")
  public String helloWorldInternationalized() {
    return messageSource.getMessage(
        "good.morning.message", null, "Default Message", LocaleContextHolder.getLocale());
  }
}
