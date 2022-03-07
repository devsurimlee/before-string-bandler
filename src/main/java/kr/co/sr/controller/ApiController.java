package kr.co.sr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/info")
    public String test() {

        return "ok 200!";
    }

}
