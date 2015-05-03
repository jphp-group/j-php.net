package org.develnext.jphp.site.twig;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Filter;
import org.pegdown.PegDownProcessor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwigExtension extends AbstractExtension {
    @Override
    public Map<String, Filter> getFilters() {
        Map<String, Filter> filters = new HashMap<>();

        filters.put("markdown", new MarkdownFilter());

        return filters;
    }

    public class MarkdownFilter implements Filter {
        @Override
        public Object apply(Object input, Map<String, Object> args) {
            PegDownProcessor processor = new PegDownProcessor();
            return processor.markdownToHtml(input.toString());
        }

        @Override
        public List<String> getArgumentNames() {
            return Arrays.asList();
        }
    }
}
