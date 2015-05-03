package org.develnext.jphp.site.controller;

import org.develnext.jphp.site.github.GithubWikiResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/wiki")
public class WikiController {
    protected GithubWikiResolver resolver = new GithubWikiResolver("jphp-compiler/jphp");

    @RequestMapping
    public String index() {
        return "redirect:/wiki/Home";
    }

    @RequestMapping("/{page}")
    public String page(@PathVariable("page") String path, ModelMap vars, HttpServletResponse response) {
        vars.put("currPage", "documentation");

        if ("Getting-started".equalsIgnoreCase(path)) {
            vars.put("currPage", "getstarted");
        }

        vars.put("path", path);
        vars.put("title", path.replace("-", " ").replace("+", " "));

        String source = resolver.get(path);

        if (source == null) {
            response.setStatus(404);
        }

        vars.put("sidebar", resolver.get("_Sidebar"));
        vars.put("source", source);

        return "wiki/page";
    }
}
