package com.bycc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/caseOpening")
public class CaseOpeningCtrl {

    /**
     * 首页
     */
    @RequestMapping
    public String index() {
        return "/pages/caseOpening/index";
    }
}
