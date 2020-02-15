package com.cd.o2o.web.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller /*注意不要用@RestController,因为它返回的是json数据格式,而我们要的是html页面*/
public class HomeController {

    @RequestMapping(value = "/home")
    public String home(){
        return "home";
    }

}