package kr.co.sr.dto;


import lombok.Data;

@Data
public class ApiDto {

    private final String url;                   // input 데이터
    private final int parsingType;              // 파싱타입  0:태그제외, 1: text전체
    private final int bundleUnit;               // 묶음단위
    private final String quotient;              // 몫
    private final String remainder;             // 나머지


}
