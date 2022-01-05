package com.spatial.spatialbrain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class VitessceController {
    @RequestMapping("/vitessce")
    public String toVitessce() {
        return "vitessce";
    }
}

