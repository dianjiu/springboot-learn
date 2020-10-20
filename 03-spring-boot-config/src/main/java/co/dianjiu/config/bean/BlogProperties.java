package co.dianjiu.config.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BlogProperties {
	
	@Value("${ncun.blog.name}")
	private String name;
	
	@Value("${ncun.blog.desc}")
	private String desc;

	@Value("${ncun.blog.site}")
	private String site;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
}
