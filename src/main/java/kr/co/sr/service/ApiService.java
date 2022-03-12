package kr.co.sr.service;

import kr.co.sr.dto.ApiDto;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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


        URL url = null;
        try {
            url = new URL(dto.getUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(1000);
            con.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
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
        Arrays.sort(engArr, Comparator.<String, Character>comparing(s -> Character.toUpperCase(s.charAt(0)))
                .thenComparing(s -> s));
        Arrays.sort(numArr);

        int cnt = 0;
        String data = "";
        while (cnt < engArr.length && cnt < numArr.length) {
            data += engArr[cnt];
            data += numArr[cnt];
            cnt++;
        }

        if (cnt < engArr.length && numArr.length < engArr.length) {
            String[] copyArr = Arrays.copyOfRange(engArr, cnt, engArr.length);
            data += String.join("", copyArr);
        }

        if (cnt < numArr.length && engArr.length < numArr.length) {
            String[] copyArr = Arrays.copyOfRange(numArr, cnt, numArr.length);
            data += String.join("", copyArr);
        }

        int totalSize = data.length();
        int remainderNum = totalSize % dto.getBundleUnit();

        dto.setResult(data.substring(0, data.length() - remainderNum));
        dto.setRemainder(data.substring(data.length() - remainderNum));

        if (totalSize < dto.getBundleUnit()) {
            dto.setResult("출력묶음단위가 전체데이터길이보다 큽니다. 숫자를 더 줄여주세요. (데이터 길이: " + totalSize + ")");
        }


        return dto;
    }

    /**
     * 데이터에서 필요없는 부분 삭제처리
     * 
     * @param str
     * @param pattern
     * @return
     */
    private String filteringPattern(String str, String pattern) {
        return str.replaceAll(pattern, "");
    }

    /**
     * 데이터가 정규식에 맞는지 체크
     * 
     * @param str
     * @param pattern
     * @return
     */
    private boolean checkPattern(String str, String pattern) {
        Pattern pt = Pattern.compile(pattern);
        Matcher mc = pt.matcher(str);

        if (mc.matches()) {
            return true;
        }
        return false;

    }

}
