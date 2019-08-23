package com.henc.cdrs.coprcp.web;

import com.henc.cdrs.coprcp.model.CoprcpInfo;
import com.henc.cdrs.coprcp.service.CoprcpInfoService;
import com.henc.dream.util.CamelCaseMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/coprcp")
public class CoprcpInfoController {

    @Autowired
    private CoprcpInfoService coprcpInfoService;

    @GetMapping("/list")
    public String list(Model model) {
        return "views/coprcp/coprcpList";
    }

    @GetMapping("/coprcpGridList")
    public @ResponseBody
    List<CamelCaseMap> coprcpGridList(CoprcpInfo coprcpInfo) {
        return coprcpInfoService.findCoprcpsBy(coprcpInfo);
    }

    @PostMapping(value = "/coprcpGridSave")
    public @ResponseBody
    CoprcpInfo coprcpGridSave(CoprcpInfo coprcpInfo) {
        coprcpInfoService.saveCoprcpList(coprcpInfo.getCoprcpInfos());
        return coprcpInfo;
    }

    @GetMapping("/ctrcList")
    public String ctrcListPopup(Model model) {
        return "views/coprcp/ctrcListPopup";
    }
}
