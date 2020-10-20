package co.dianjiu.config.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="ncun.blog")
public class ConfigBean {
    private String name;
    private String desc;
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
