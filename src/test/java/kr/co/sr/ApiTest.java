package kr.co.sr;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.sr.controller.ParseController;
import kr.co.sr.dto.ExposureType;
import kr.co.sr.dto.ParseRequestDto;
import kr.co.sr.dto.ParseResponseDto;
import kr.co.sr.service.ParseService;
import kr.co.sr.util.ApiConst;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.InetAddress;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@WebMvcTest(controllers = ParseController.class)
public class ApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private ParseService apiService;


    @Test
    public void getUrlContent_통신테스트() throws Exception {

        String addr = InetAddress.getLocalHost().getHostAddress();
        String port = Integer.toString(applicationContext.getBean(Environment.class).getProperty("server.port", Integer.class, 8080));

        String url = "http://" + addr + ":" + port;
        ExposureType parsingType = ExposureType.REMOVE_HTML;
        int bundleUnit = 6;
        String result = "A1a2a5a9BbbbCcccDeeilstt";
        String remainder = "ttz";

        ParseRequestDto params = new ParseRequestDto(url, parsingType, bundleUnit);
        ParseResponseDto res = new ParseResponseDto(result, remainder);

        String content = mapper.writeValueAsString(params);
        when(apiService.getUrlContent(params)).thenReturn(res);

        mockMvc.perform(post(ApiConst.API_VERSION + "/getUrlContent")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(result))
                .andExpect(jsonPath("$.remainder").value(remainder))
                .andDo(print());

    }

    @Test
    public void getUrlContent_로직테스트() throws Exception {

        String addr = InetAddress.getLocalHost().getHostAddress();
        String port = Integer.toString(applicationContext.getBean(Environment.class).getProperty("server.port", Integer.class, 8080));

        String url = "http://" + addr + ":" + port;
        ExposureType parsingType = ExposureType.REMOVE_HTML;
        int bundleUnit = 6;
        String result = "A1a2a5a9BbbbCcccDeeilstt";
        String remainder = "ttz";

        ParseRequestDto params = new ParseRequestDto(url, parsingType, bundleUnit);
        ParseResponseDto res = new ParseResponseDto(result, remainder);

        assertThat(res.getResult()).isEqualTo(result);
        assertThat(res.getRemainder()).isEqualTo(remainder);

    }

}
