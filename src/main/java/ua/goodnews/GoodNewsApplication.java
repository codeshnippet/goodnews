package ua.goodnews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import ua.goodnews.converters.SyndEntryToNeewsEntryConverter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by acidum on 1/20/17.
 */

@SpringBootApplication
public class GoodNewsApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GoodNewsApplication.class, args);
    }

    @Bean("conversionService")
    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();
        ConversionService object = bean.getObject();
        return object;
    }

    private Set<Converter> getConverters() {
        Set<Converter> converters = new HashSet<Converter>();

        converters.add(new SyndEntryToNeewsEntryConverter());

        return converters;
    }
}
