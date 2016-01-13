package me.artyushov.blog;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: artyushov
 * Date: 2016-01-12 23:53
 */
@RestController
public class IndexController {

    @RequestMapping("/open")
    public String hello() {
        return "This link is available to everyone";
    }

    @RequestMapping("/onlyForUsers")
    public String onlyForUsers() {
        return "This link is available for every authenticated user";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "This link is available only to admins";
    }
}
