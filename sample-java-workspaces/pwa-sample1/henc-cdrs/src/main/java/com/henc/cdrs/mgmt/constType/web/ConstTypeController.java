package com.henc.cdrs.mgmt.constType.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.mgmt.constType.model.ConstType;
import com.henc.cdrs.mgmt.constType.model.Ocpt;
import com.henc.cdrs.mgmt.constType.service.ConstTypeService;
import com.henc.dream.util.CamelCaseMap;

@Controller
@RequestMapping("/mgmt/constType")
public class ConstTypeController extends BaseController {

    @Autowired
    private ConstTypeService constTypeService;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/")
    public String standard(Model model) {
        return "views/mgmt/constType/constType";
    }

    @GetMapping("/gridList")
    public @ResponseBody
    List<CamelCaseMap> standardGridList(ConstType constType) {
        return constTypeService.selectConstTypeGrdList(constType);
    }

    @PostMapping(value = "/gridSave")
    public @ResponseBody
    ConstType constTypeGridSave(ConstType constType) {
        constTypeService.saveConstTypeList(constType.getConstTypes());
        return constType;
    }

    @GetMapping("/gridDetailList")
    public @ResponseBody
    List<CamelCaseMap> ocptGridList(Ocpt ocpt) {
        return constTypeService.selectOcptGrdList(ocpt);
    }

    @PostMapping(value = "/gridDetailSave")
    public @ResponseBody
    Ocpt ocptGridSave(Ocpt ocpt) {
        constTypeService.saveOcptList(ocpt.getOcpts());
        return ocpt;
    }
}
