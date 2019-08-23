package com.henc.cdrs.mgmt.progress.web;

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
import com.henc.cdrs.mgmt.progress.model.Progress;
import com.henc.cdrs.mgmt.progress.service.ProgressService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.dream.util.CamelCaseMap;

@Controller
@RequestMapping("/mgmt/progress")
public class ProgressController extends BaseController {

    @Autowired
    private ProgressService progressService;

    @Autowired
    HttpServletRequest request;

    /* 현장장비관리 화면 */
    @GetMapping("/")
    public String list(Model model) {
        return "views/mgmt/progress/progress";
    }

    /* 현장장비관리 상단 리스트 */
    @GetMapping("/gridList")
    public @ResponseBody
    List<CamelCaseMap> gridList(Progress progress) {
        UserDetail userDetail = getUserContext(request);
        String deptCd = userDetail.getDeptCd();
        progress.setDeptCd(deptCd);
        return progressService.selectProgressGrdList(progress);
    }

    /* 현장장비관리 상단 저장 */
    @PostMapping(value = "/gridSave")
    public @ResponseBody Progress gridSave(Progress progress) {
        progressService.saveProgressList(progress.getProgresses());
        return progress;
    }

}
