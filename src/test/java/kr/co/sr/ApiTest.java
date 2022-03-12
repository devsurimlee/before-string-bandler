package kr.co.sr;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.sr.controller.ApiController;
import kr.co.sr.dto.ApiDto;
import kr.co.sr.service.ApiService;
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

import javax.jws.WebService;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@WebMvcTest(controllers = ApiController.class)
public class ApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private ApiService apiService;


    @Test
    public void getUrlContent_통신테스트() throws Exception {

        String addr = InetAddress.getLocalHost().getHostAddress();
        String port = Integer.toString(applicationContext.getBean(Environment.class).getProperty("server.port", Integer.class, 8080));

        String url = "http://" + addr + ":" + port;
        int parsingType = 0;
        int bundleUnit = 6;
        String result = "A1a2a5a9BbbbCcccDeeilstt";
        String remainder = "ttz";

        ApiDto params = new ApiDto();
        params.setUrl(url);
        params.setParsingType(parsingType);
        params.setBundleUnit(bundleUnit);

        ApiDto res = new ApiDto();
        res.setResult(result);
        res.setRemainder(remainder);

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
        int parsingType = 0;
        int bundleUnit = 6;
        String result = "A1a2a5a9BbbbCcccDeeilstt";
        String remainder = "ttz";

        ApiDto params = new ApiDto();
        params.setUrl(url);
        params.setParsingType(parsingType);
        params.setBundleUnit(bundleUnit);

        ApiDto res = new ApiService().getUrlContent(params);

        assertThat(res.getResult()).isEqualTo(result);
        assertThat(res.getRemainder()).isEqualTo(remainder);

    }

    @Test
    public void hello_test() throws Exception {
        String hello = "hello";

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));

    }

}
