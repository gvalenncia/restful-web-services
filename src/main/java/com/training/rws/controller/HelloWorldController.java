package com.training.rws.controller;

import com.training.rws.dto.HelloWorldDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldDTO helloWorldBean(){
        return new HelloWorldDTO("Hello World");
    }

    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldDTO helloWorldPathVariable(@PathVariable String name){
        return new HelloWorldDTO(String.format("Hello World, %s", name));
    }

    @GetMapping(path = "/hello-world/i18n")
    public String helloWorldi18n(@RequestHeader(name="Accept-Language", required = false) Locale locale){
        return messageSource.getMessage("good.morning.message", null, locale);
    }
}
