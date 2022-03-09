package project.logback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String helloController() {
        log.info("Hello Controller...");
        return "ok";
    }
}
