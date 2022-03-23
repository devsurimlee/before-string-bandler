package kr.co.sr.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class ParseResponseDto {

    @ApiModelProperty(value = "데이터를 bundleUnit으로 나누고 남은 몫", example = "")
    private final String result;

    @ApiModelProperty(value = "데이터를 bundleUnit으로 나누고 나머지", example = "")
    private final String remainder;

    public ParseResponseDto(final OutputUnit outputUnit) {
        this.result = outputUnit.getResult();
        this.remainder = outputUnit.getRemainder();
    }

    public ParseResponseDto(final ParseResponseDto parseResponseDto) {
        this.result = parseResponseDto.getResult();
        this.remainder = parseResponseDto.getRemainder();
    }

}
