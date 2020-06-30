package co.dianjiu.aspect.controller;

import co.dianjiu.aspect.annotation.MyLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @MyLog
    @GetMapping("/sourceC/{source_name}")
    @ResponseBody
    public String sourceC(@PathVariable("source_name") String sourceName){
        return "你正在访问sourceC资源";
    }
}
