package co.dianjiu.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = { "hello" })
@RequestMapping("/hello")
@RestController
public class HelloController {

    @GetMapping("/sayHello")
    @ApiOperation(value = "sayHello")
    public String sayHello(){
        return "hello world!";
    }


    @GetMapping("/sourceC/{source_name}")
    public String sourceC(@PathVariable("source_name") String sourceName){
        return "你正在访问sourceC资源";
    }
}
