package co.dianjiu.aspect.controller;

import co.dianjiu.aspect.annotation.WebLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getOne")
    @WebLog(name = "查询", intoDb = true)
    public String getOne(Long id, String name) {

        return "1234";
    }
}

