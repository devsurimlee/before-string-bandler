package kr.co.sr.dto;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiDtoTest {

    @Test
    public void test() {
        String url = "<div>A0a1B2b3</div>";
        int parsingType = 0;
        int bundleUnit = 3;
        String quotient = "A0a1B2";
        String remainder = "b3!";

        ApiDto dto = new ApiDto(url, parsingType, bundleUnit, quotient, remainder);


        assertThat(dto.getUrl()).isEqualTo(url);
        assertThat(dto.getParsingType()).isEqualTo(parsingType);
        assertThat(dto.getBundleUnit()).isEqualTo(bundleUnit);
        assertThat(dto.getQuotient()).isEqualTo(quotient);
        assertThat(dto.getRemainder()).isEqualTo(remainder);


    }


}
