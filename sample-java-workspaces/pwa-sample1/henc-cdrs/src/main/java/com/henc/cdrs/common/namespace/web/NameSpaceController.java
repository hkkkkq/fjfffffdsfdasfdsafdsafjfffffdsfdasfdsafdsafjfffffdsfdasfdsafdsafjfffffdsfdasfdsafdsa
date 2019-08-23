package com.henc.cdrs.common.namespace.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.henc.dream.util.CamelCaseMap;
import com.henc.cdrs.common.namespace.model.NameSpace;
import com.henc.cdrs.common.namespace.service.NameSpaceService;

@RestController
@RequestMapping("/nameSpace")
public class NameSpaceController {
    @Autowired
    private NameSpaceService nameSpaceService;
    
    @GetMapping("/getNameSpace")
    public @ResponseBody CamelCaseMap getNameSpace(NameSpace nameSpace) {
        return nameSpaceService.getNameSpace(nameSpace);
    }
}