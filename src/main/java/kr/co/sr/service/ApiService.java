package kr.co.sr.service;

import kr.co.sr.dto.ApiDto;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ApiService {

    public ApiDto getUrlContent(ApiDto dto) {

        String html = "";
        String baseStr = "";
        String engStr = "";
        String numStr = "";

        final String pattern_tag = "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>";
        final String pattern_eng_and_num = "[^a-zA-Z0-9]";
        final String pattern_eng = "[^a-zA-z]";
        final String pattern_num = "[^0-9]";
        final String pattern_protocol = "^((http|https)://).+";
        final String pattern_url = "^((http|https)://)+([a-zA-Z0-9가-힣])+.+([a-zA-Z0-9-_.?=/\"'#])";

        boolean isProtocol = checkPattern(dto.getUrl(), pattern_protocol);

        if (!isProtocol) {
            dto.setResult("경로에 프로토콜이 필요합니다. http:// 혹은 https://를 추가해주세요.");
            return dto;
        }

//        boolean isUrl = checkPattern(dto.getUrl(), pattern_url);
//
//        if (!isUrl) {
//            dto.setResult("잘못된 경로입니다.");
//            return dto;
//        }

        URL myURL = null;
        try {
            myURL = new URL(dto.getUrl());
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
                    baseStr = filteringPattern(html, pattern_tag).replace(" ", "");
                    break;
                case 1:
                    baseStr = html;
                    break;
                default:
                    dto.setResult("없는 파싱타입입니다. 다시 확인해주세요.");
                    return dto;
            }
        } catch (Exception e) {
            dto.setResult("잘못된 경로입니다.");
            return dto;
        }

        baseStr = filteringPattern(baseStr, pattern_eng_and_num);       //영어-숫자
        engStr = filteringPattern(baseStr, pattern_eng);                //영어만
        numStr = filteringPattern(baseStr, pattern_num);                //숫자만

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
        dto.setRemainder(data.substring(data.length() - remainderNum));


        return dto;
    }

    private String filteringPattern(String str, String pattern) {
        return str.replaceAll(pattern, "");
    }

    private boolean checkPattern(String str, String pattern) {
        Pattern pt = Pattern.compile(pattern);
        Matcher mc = pt.matcher(str);

        if (mc.matches()) {
            return true;
        }
        return false;

    }

}
