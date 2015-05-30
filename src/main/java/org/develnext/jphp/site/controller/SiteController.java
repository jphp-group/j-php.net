package org.develnext.jphp.site.controller;

import org.develnext.jphp.site.github.GithubWikiResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SiteController {
    protected GithubWikiResolver resolver = new GithubWikiResolver("jphp-compiler/jphp");

    @RequestMapping
    public String index(ModelMap vars) {
        vars.put("currPage", "index");
        return "site/index";
    }
    
    @RequestMapping("donate/")
    public String donate(ModelMap vars) {
        vars.put("currPage", "donate");
        return "site/donate";
    }

    @RequestMapping("documentation/")
    public String documentation(ModelMap vars) {
        vars.put("currPage", "documentation");
        vars.put("source", resolver.get("_DOCUMENTATION"));
        vars.put("path", "_DOCUMENTATION");
        vars.put("title", "Documentation");

        return "site/wiki";
    }

    @RequestMapping("faq/")
    public String faq(ModelMap vars) {
        vars.put("currPage", "faq");
        vars.put("source", resolver.get("_FAQ"));
        vars.put("path", "_FAQ");
        vars.put("title", "F.A.Q.");

        return "site/wiki";
    }

    @RequestMapping("download/")
    public String download(ModelMap vars) {
        vars.put("currPage", "download");
        vars.put("source", resolver.get("_DOWNLOAD"));
        vars.put("path", "_DOWNLOAD");
        vars.put("title", "Download");

        return "site/wiki";
    }
}
