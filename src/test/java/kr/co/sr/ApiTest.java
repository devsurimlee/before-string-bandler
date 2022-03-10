package kr.co.sr;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.sr.controller.ApiController;
import kr.co.sr.dto.ApiDto;
import kr.co.sr.util.ApiConst;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.InetAddress;

import static org.assertj.core.api.Assertions.assertThat;
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


    @Test
    public void getUrlContent_success() throws Exception {

        String addr = InetAddress.getLocalHost().getHostAddress();
        String port = Integer.toString(applicationContext.getBean(Environment.class).getProperty("server.port", Integer.class, 8080));

        String url = "http://" + addr + ":" + port;
        int parsingType = 0;
        int bundleUnit = 6;
        String quotient = "A1a2a5a9BbbbCcccDeeilstt";
        String remainder = "ttz";

        ApiDto dto = new ApiDto();
        dto.setUrl(url);
        dto.setParsingType(parsingType);
        dto.setBundleUnit(bundleUnit);

        String content = mapper.writeValueAsString(dto);

        mockMvc.perform(post(ApiConst.API_VERSION + "/getUrlContent")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quotient").value(quotient))
                .andExpect(jsonPath("$.remainder").value(remainder))
                .andDo(print());

    }

    @Test
    public void hello_test() throws Exception {
        String hello = "hello";

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));

    }
    
}
