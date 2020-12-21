package co.dianjiu.custome.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ThemeConfig {
    @Value("${theme.name}")
    private String name;
}
