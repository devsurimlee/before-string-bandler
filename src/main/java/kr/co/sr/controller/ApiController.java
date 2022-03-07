package kr.co.sr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {


    /**
     * 테스트 코드 예시
     * @return
     */
    @GetMapping("/hello")
    public String hello() {

        return "hello";
    }

}
