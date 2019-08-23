package com.henc.cdrs.mgmt.equipment.web;

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
import com.henc.cdrs.mgmt.equipment.model.Equipment;
import com.henc.cdrs.mgmt.equipment.model.EquipmentDetl;
import com.henc.cdrs.mgmt.equipment.service.EquipmentService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.dream.util.CamelCaseMap;

@Controller
@RequestMapping("/mgmt/equipment")
public class EquipmentController extends BaseController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/list")
    public String list(Model model) {
        return "views/mgmt/equipment/equipmentList";
    }

    @GetMapping("/gridList")
    public @ResponseBody
    List<CamelCaseMap> gridList(Equipment equipment) {
        UserDetail userDetail = getUserContext(request);
        String bizhdofCd = userDetail.getBizhdofCd();
        equipment.setBizhdofCd(bizhdofCd);
        return equipmentService.selectEquipGrdList(equipment);
    }

    /* 장비등록 상단 저장 */
    @PostMapping(value = "/gridSave")
    public @ResponseBody
    Equipment gridSave(Equipment equipment) {
        equipmentService.saveEquipmentList(equipment.getEquipments(), getUserContext(request).getBizhdofCd());
        return equipment;
    }

    // 장비 하단 조회
    @GetMapping("/gridDetailList")
    public @ResponseBody
    List<CamelCaseMap> gridDetailList(EquipmentDetl equipmentDetl) {
        UserDetail userDetail = getUserContext(request);
        String bizhdofCd = userDetail.getBizhdofCd();
        equipmentDetl.setBizhdofCd(bizhdofCd);
        return equipmentService.selectEquipmentGrdDetlList(equipmentDetl);
    }

    /* 장비등록 하위 저장 */
    @PostMapping(value = "/gridDetailSave")
    public @ResponseBody
    EquipmentDetl gridDetailSave(EquipmentDetl equipmentDetl) {
        equipmentService.saveEquipmentDetlList(equipmentDetl.getEquipmentDetls(), getUserContext(request).getBizhdofCd());
        return equipmentDetl;
    }

    /* 장비등록 동의어 팝업 */
    @GetMapping(value = "/gridDetailSynPopList")
    public @ResponseBody
    List<CamelCaseMap> gridDetailSynPopList(Equipment equipment) {
        UserDetail userDetail = getUserContext(request);
        String bizhdofCd = userDetail.getBizhdofCd();
        equipment.setBizhdofCd(bizhdofCd);
        return equipmentService.selectEquipmentSynPoplList(equipment);
    }

    /* 장비등록 동의어 팝업 화면 */
    @GetMapping("/equipmentListModal")
    public String equipmentListModal(Model model) {
        return "views/mgmt/equipment/equipmentListModal";
    }

}
