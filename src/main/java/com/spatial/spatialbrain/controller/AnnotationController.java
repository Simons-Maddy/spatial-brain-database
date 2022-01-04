package com.spatial.spatialbrain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AnnotationController {
    @RequestMapping("/annotation")
    public String toannotation() {
        return "annotation";
    }
}

