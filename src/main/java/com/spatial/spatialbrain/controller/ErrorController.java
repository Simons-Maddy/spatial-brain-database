package com.spatial.spatialbrain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/404")
    public String to404() {
        return "error/404";
    }

    @RequestMapping("/500")
    public String to500() {
        return "error/500";
    }
}


