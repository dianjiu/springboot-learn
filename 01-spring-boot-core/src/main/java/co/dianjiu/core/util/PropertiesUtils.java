package co.dianjiu.core.util;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;
/**
 * added by dianjiu 2019-12-23
 * 通过${key}作为name格式调用getPropertiesValue()方法
 */
@Component
public class PropertiesUtils implements EmbeddedValueResolverAware {

    private static StringValueResolver stringValueResolver;

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        stringValueResolver = resolver;
    }

    public static String getPropertiesValue(String name){
        return stringValueResolver.resolveStringValue(name);
    }
}
