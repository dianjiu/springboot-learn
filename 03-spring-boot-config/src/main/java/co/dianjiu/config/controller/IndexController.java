package co.dianjiu.config.controller;


import co.dianjiu.config.bean.BlogProperties;
import co.dianjiu.config.bean.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {
	@Autowired
	private BlogProperties blogProperties;
	@Autowired
	private ConfigBean configBean;
	
	@RequestMapping("/")
	String index() {
		return blogProperties.getName()+"，"+configBean.getSite();
	}
}
