package co.dianjiu.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(){
        return "hello world!";
    }


    @GetMapping("/sourceC/{source_name}")
    public String sourceC(@PathVariable("source_name") String sourceName){
        return "你正在访问sourceC资源";
    }
}
