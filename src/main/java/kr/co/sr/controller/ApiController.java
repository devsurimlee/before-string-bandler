package kr.co.sr.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.sr.dto.ApiDto;
import kr.co.sr.service.ApiService;
import kr.co.sr.util.ApiConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping(ApiConst.API_VERSION)
public class ApiController {

    @Autowired
    private ApiService apiService;

    @PostMapping("/getUrlContent")
    @ApiOperation(value = "URL 컨텐츠 조회", notes = "URL 컨텐츠를 조건에 맞게 필터링하고 조회한다.")
    public ApiDto getUrlContent(@RequestBody ApiDto dto) {
        try {
            return apiService.getUrlContent(dto);
        } catch (Exception e) {
            return dto;
        }
    }

    @ApiIgnore
    @GetMapping("/hello")
    public String hello() {

        return "hello";
    }

}
