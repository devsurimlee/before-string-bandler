package kr.co.sr.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.Min;



@Getter
@RequiredArgsConstructor
public class ParseRequestDto {
    @ApiModelProperty(value = "사이트주소", example = "https://www.google.com/", required = true)
    private final String url;

    @ApiModelProperty(value = "파싱타입", example = "REMOVE_HTML")
    private final ExposureType parsingType;

    @ApiModelProperty(value = "데이터 묶음단위", example = "20")
    @Min(value = 1, message = "1이상의 수를 입력해주세요.")
    private final int bundleUnit;

}
