package org.develnext.jphp.site.github;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.io.IOUtils;
import org.pegdown.Extensions;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.WikiLinkNode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

public class GithubWikiResolver {
    protected final String repository;

    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build();

    private LinkRenderer linkRenderer = new LinkRenderer() {
        @Override
        public Rendering render(WikiLinkNode node) {
            try {
                String url = "/wiki/" + URLEncoder.encode(node.getText().replace(' ', '-'), "UTF-8");
                return new Rendering(url, node.getText());
            } catch (UnsupportedEncodingException e) {
                throw new IllegalStateException();
            }
        }
    };

    public GithubWikiResolver(String repository) {
        this.repository = repository;
    }

    public String get(String page) {
        String markdown;

        PegDownProcessor processor = new PegDownProcessor(Extensions.ALL);

        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(
                    "https://raw.githubusercontent.com/wiki/" + repository + "/" + page + ".md"
            ).openConnection();

            urlConnection.connect();

            if (urlConnection.getResponseCode() == 404) {
                return null;
            } else {
                markdown = IOUtils.toString(urlConnection.getInputStream());
                markdown = processor.markdownToHtml(markdown, linkRenderer);

                cache.put(repository + "/" + page, markdown);

                return markdown;
            }
        } catch (IOException e) {
            return null;
        }
    }
}
