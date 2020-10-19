package co.dianjiu.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    @RequestMapping("/show")
    public String showInfo(Model model){
        model.addAttribute("msg","Thymeleaf入门案例...");
        return "index";
    }

    @RequestMapping("/404")
    public String show404(Model model){
        return "404";
    }
}
