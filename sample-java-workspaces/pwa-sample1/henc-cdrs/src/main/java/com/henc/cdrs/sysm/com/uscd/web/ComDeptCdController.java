package com.henc.cdrs.sysm.com.uscd.web;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.sysm.com.uscd.model.ComDeptCd;
import com.henc.cdrs.sysm.com.uscd.service.ComDeptCdService;
import com.henc.dream.util.CamelCaseMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sysm/com/uscd")
public class ComDeptCdController extends BaseController {

    @Autowired
    ComDeptCdService comDeptCdService;

    @GetMapping({"/comDeptCdList", "/comDeptCdList/{searchDeptNm}"})
    public String deptListModal(@PathVariable(required = false) String searchDeptNm, Model model) {
        return "views/sysm/com/uscd/deptListModal";
    }

    @GetMapping("/deptCdListSelectPopup")
    public String deptCdListSelectPopup(Model model) {
        return "views/sysm/com/uscd/deptCdListSelectPopup";
    }

    @GetMapping("/comDeptCdGridList")
    public @ResponseBody
    List<CamelCaseMap> comDeptCdGridList(ComDeptCd comDeptCd) {
        return comDeptCdService.getComDeptCdList(comDeptCd);
    }

    /**
     * 부서코드 계층쿼리 데이터 조회
     *
     * @param map
     * @return
     */
    @GetMapping("/gridComDeptCdHierachyList")
    public @ResponseBody
    List<CamelCaseMap> gridComDeptCdHierachyList(@RequestParam Map<String, String> map) {
        return comDeptCdService.gridComDeptCdHierachyList(map);
    }

}
