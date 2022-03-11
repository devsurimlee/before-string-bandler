package kr.co.sr.dto;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiDtoTest {

    @Test
    public void dto_test() {
        String url = "http://localhost:8008/";
        int parsingType = 0;
        int bundleUnit = 3;
        String result = "A0a1B2";
        String remainder = "b3!";

        ApiDto dto = new ApiDto(url, parsingType, bundleUnit, result, remainder);


        assertThat(dto.getUrl()).isEqualTo(url);
        assertThat(dto.getParsingType()).isEqualTo(parsingType);
        assertThat(dto.getBundleUnit()).isEqualTo(bundleUnit);
        assertThat(dto.getresult()).isEqualTo(result);
        assertThat(dto.getRemainder()).isEqualTo(remainder);


    }


}
