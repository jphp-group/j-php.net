package org.develnext.jphp.site;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.LoaderException;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.spring.PebbleViewResolver;
import org.develnext.jphp.site.twig.TwigExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;

import java.io.*;

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
        PebbleEngine engine = new PebbleEngine(new ClasspathLoader() {
            @Override
            public Reader getReader(String templateName) throws LoaderException {

                InputStreamReader isr = null;
                Reader reader = null;

                InputStream is = null;

                // append the prefix and make sure prefix ends with a separator
                // character
                StringBuilder path = new StringBuilder("");
                if (getPrefix() != null) {
                    path.append(getPrefix());
                }

                String location = path.toString() + templateName + (getSuffix() == null ? "" : getSuffix());

                // perform the lookup
                ClassLoader rcl = Thread.currentThread().getContextClassLoader();
                is = rcl.getResourceAsStream(location);

                if (is == null) {
                    throw new LoaderException(null, "Could not find template \"" + location + "\"");
                }

                try {
                    isr = new InputStreamReader(is, "UTF-8");
                    reader = new BufferedReader(isr);
                } catch (UnsupportedEncodingException e) {
                }

                return reader;
            }
        });

        engine.addExtension(twigExtension());

        return engine;
    }

    @Bean
    public ViewResolver viewResolver() {
        PebbleViewResolver viewResolver = new PebbleViewResolver();
        viewResolver.setPrefix("\\templates\\");
        viewResolver.setSuffix(".twig");
        viewResolver.setPebbleEngine(pebbleEngine());

        return viewResolver;
    }
}
