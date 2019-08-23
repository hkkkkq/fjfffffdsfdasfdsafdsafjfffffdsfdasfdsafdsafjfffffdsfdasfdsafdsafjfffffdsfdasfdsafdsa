package com.henc.cdrs.common.code.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.common.code.model.Code;
import com.henc.cdrs.common.code.service.CodeService;

/**
 * 부서에 대한 사용자 접점을 이루고 서비스한다.
 *
 * @author YongSang
 */
@RestController
@RequestMapping("/code")
public class CodeController {
    @Autowired
    private CodeService codeService;
    
    /**
     * 보통 화면을 구성할 때 공통코드를 많게는 10개 이상씩 사용하여 구성된다.
     * 만약 10개의 공통코드가 필요하기 때문에 10번의 http 요청을 통해 화면을 구성한다면
     * Client가 체감하는 성능은 매우 낮게 느껴질 것이다.
     * 그렇기 때문에 화면 구성에 필요한 공통코드 목록은 한 번의 요청에 모두 가져와
     * 화면을 구성하는 것이 화면 렌더링 성능을 높이는데 크게 도움이된다.
     *
     * @param ids 공통코드 아이디
     * @return 공통코드 목록
     */    

    @GetMapping("/getCodeList/{ids}")
    public Map<String, List<CamelCaseMap>> findAllById(@PathVariable String[] ids) {
        Map<String, List<CamelCaseMap>> codes = new HashMap<>();
        for (String id : ids) {
            codes.put(CamelCaseMap.convertUnderscoreNameToPropertyName(id), codeService.getCodeList(id));
        }
        return codes;          
    }
    
    @GetMapping("/getCodeAttributeList")
    public @ResponseBody List<CamelCaseMap> getCodeAttributeList(Code code) {
        return codeService.selectCodeAttributeList(code);
    }
}