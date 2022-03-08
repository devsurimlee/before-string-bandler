package kr.co.sr.dto;


import lombok.Data;

@Data
public class ApiDto {

    private String url;                   // input 데이터
    private int parsingType;              // 파싱타입  0:태그제외, 1: text전체
    private int bundleUnit;               // 묶음단위
    private String quotient;              // 몫
    private String remainder;             // 나머지


    public ApiDto(String url, int parsingType, int bundleUnit, String quotient, String remainder) {
    }

    public ApiDto() {

    }
}
