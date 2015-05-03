package org.develnext.jphp.site;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.spring.PebbleViewResolver;
import org.develnext.jphp.site.twig.TwigExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;

@SpringBootApplication
public class JphpSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(JphpSiteApplication.class, args);
    }

    @Bean
    public TwigExtension twigExtension() {
        return new TwigExtension();
    }

    @Bean
    public PebbleEngine pebbleEngine() {
        PebbleEngine engine = new PebbleEngine(new ClasspathLoader());

        engine.addExtension(twigExtension());

        return engine;
    }

    @Bean
    public ViewResolver viewResolver() {
        PebbleViewResolver viewResolver = new PebbleViewResolver();
        viewResolver.setPrefix("templates/");
        viewResolver.setSuffix(".twig");
        viewResolver.setPebbleEngine(pebbleEngine());

        return viewResolver;
    }
}
