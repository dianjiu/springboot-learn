package co.dianjiu.exception.controller;

import co.dianjiu.exception.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("/test")
    public void test() {
        demoService.test();
    }
}
