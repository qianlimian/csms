package org.smartframework.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhaochuanfeng
 */
@Controller
@RequestMapping("smart/tutorials")
public class TutorialCtrl {
	
	@RequestMapping("/fluid_layout")
    public String fixedLayout() {
		return "/smart/tutorials/smart_fluid_layout";
    }
	
	@RequestMapping("/fixed_layout")
    public String fluidLayout() {
		return "/smart/tutorials/smart_fixed_layout";
    }
	
	@RequestMapping("/input")
    public String input() {
		return "/smart/tutorials/smart_input";
    }

    @RequestMapping("/bootstrap_layout")
    public String bootstrapLayout() {
        return "/smart/tutorials/bootstrap_layout";
    }
}
