package kr.co.sr.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ApiDto {
    @ApiModelProperty(value = "사이트주소", example = "https://www.google.com/", required = true)
    private String url;

    @ApiModelProperty(value = "파싱타입 - 0:태그제외, 1: 텍스트전체", example = "0")
    private int parsingType;

    @ApiModelProperty(value = "데이터 묶음단위", example = "20")
    private int bundleUnit;

    @ApiModelProperty(value = "데이터를 bundleUnit으로 나누고 남은 몫", example = "")
    private String result;

    @ApiModelProperty(value = "데이터를 bundleUnit으로 나누고 나머지", example = "")
    private String remainder;

    

    public ApiDto(String url, int parsingType, int bundleUnit, String result, String remainder) {
    }

    public ApiDto() {
        this.url = "https://www.google.com/";
        this.parsingType = 0;
        this.bundleUnit = 20;

    }
}
