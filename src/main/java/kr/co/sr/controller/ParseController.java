package kr.co.sr.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.sr.dto.ParseRequestDto;
import kr.co.sr.dto.ParseResponseDto;
import kr.co.sr.service.ParseService;
import kr.co.sr.util.ApiConst;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConst.API_VERSION)
public class ParseController {

    private final ParseService apiService;

    @PostMapping("/getUrlContent")
    @ApiOperation(value = "URL 컨텐츠 조회", notes = "URL 컨텐츠를 조건에 맞게 필터링하고 조회한다.")
    public ResponseEntity<ParseResponseDto> getUrlContent(@RequestBody @Valid final ParseRequestDto requestDto) throws Exception {
        final ParseResponseDto responseDto = apiService.getUrlContent(requestDto);
        return ResponseEntity.ok(responseDto);
    }

}
