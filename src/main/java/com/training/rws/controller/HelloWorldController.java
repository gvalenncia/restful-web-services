package com.training.rws.controller;

import com.training.rws.dto.HelloWorldDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

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
}
