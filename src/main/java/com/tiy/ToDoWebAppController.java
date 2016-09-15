package com.tiy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jessicatracy on 9/15/16.
 */
@Controller
public class ToDoWebAppController {
    @RequestMapping(path="/", method= RequestMethod.GET)
    public String home() {
        return "home";
    }
}
