package kr.co.sr.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.sr.dto.ApiDto;
import kr.co.sr.util.ApiConst;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping(ApiConst.API_VERSION)
public class ApiController {


    @PostMapping("/getUrlContent")
    @ApiOperation(value = "URL 컨텐츠 조회", notes = "URL 컨텐츠를 조건에 맞게 필터링하고 조회한다.")
    public ApiDto getUrlContent(@RequestBody ApiDto dto) {

        String html = "";
        String baseStr = "";
        String engStr = "";
        String numStr = "";

        final String pattern_tag = "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>";
        final String pattern_eng_and_num = "[^a-zA-Z0-9]";
        final String pattern_eng = "[^a-zA-z]";
        final String pattern_num = "[^0-9]";


        try {

            URL myURL = new URL(dto.getUrl());
            InputStream is = myURL.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String tmp;

            while ((tmp = br.readLine()) != null) {
                sb.append(tmp);
            }
            html = sb.toString();

            switch (dto.getParsingType()) {
                case 0:
                    baseStr = checkPattern(html, pattern_tag).replace(" ", "");
                    break;
                case 1:
                    baseStr = html;
                    break;
            }

            baseStr = checkPattern(baseStr, pattern_eng_and_num);       //영어-숫자
            engStr = checkPattern(baseStr, pattern_eng);                //영어만
            numStr = checkPattern(baseStr, pattern_num);                //숫자만

            String[] engArr = engStr.split("");
            String[] numArr = numStr.split("");
            Arrays.sort(engArr, Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
            Arrays.sort(numArr);

            Deque engQue = new LinkedList();
            engQue.addAll(new ArrayList(Arrays.asList(engArr)));

            Queue numQue = new LinkedList();
            numQue.addAll(new ArrayList(Arrays.asList(numArr)));


            String data = "";
            while (!engQue.isEmpty() || !numQue.isEmpty()) {
                if (!engQue.isEmpty()) {
                    data += engQue.pollLast();
                }
                if (!numQue.isEmpty()) {
                    data += numQue.poll();
                }
            }

            int totalSize = data.length();
            int remainderNum = totalSize % dto.getBundleUnit();

            dto.setResult(data.substring(0, data.length() - remainderNum));
            dto.setRemainder(data.substring(data.length() - remainderNum, data.length()));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    @ApiIgnore
    @GetMapping("/hello")
    public String hello() {

        return "hello";
    }

    private String checkPattern(String str, String pattern) {
        return str.replaceAll(pattern, "");
    }


}
