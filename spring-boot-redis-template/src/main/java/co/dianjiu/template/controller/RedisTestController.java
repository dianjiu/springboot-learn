package co.dianjiu.template.controller;

import co.dianjiu.template.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class RedisTestController {

    @Autowired
    RedisUtil redisUtil;

    @GetMapping(value = "save")
    public void test(){
        redisUtil.set("2020202001","15栋3单元");
        String str = String.valueOf(redisUtil.get("2020202001"));
        System.out.println(str);
    }
}
