package kr.co.sr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class OutputUnit {

    @ApiModelProperty(value = "데이터를 bundleUnit으로 나누고 남은 몫", example = "")
    private final String result;

    @ApiModelProperty(value = "데이터를 bundleUnit으로 나누고 나머지", example = "")
    private final String remainder;

    public OutputUnit(final String str, final int unitCount) {
        final int length = str.length();
        final int remainCount = length % unitCount;
        final int devideStandard = length - remainCount;
        result = str.substring(0, devideStandard);
        remainder = str.substring(devideStandard, length);
    }


}
