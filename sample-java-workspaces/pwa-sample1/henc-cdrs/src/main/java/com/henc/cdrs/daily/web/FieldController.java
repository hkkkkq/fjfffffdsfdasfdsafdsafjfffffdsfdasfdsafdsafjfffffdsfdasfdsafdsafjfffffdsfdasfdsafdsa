package com.henc.cdrs.daily.web;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.daily.model.*;
import com.henc.cdrs.daily.service.DailyCommonService;
import com.henc.cdrs.daily.service.FieldService;
import com.henc.dream.util.CamelCaseMap;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 한화건설 담당 공사일보 Controller.
 */
@Controller
@RequestMapping("/daily/field")
public class FieldController extends BaseController {

    @Autowired
    private FieldService fieldService;

    @Autowired
    private DailyCommonService dailyCommonService;

    /**
     * 현장담당 공사일보 메인.
     *
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        return "views/daily/field";
    }

    /**
     * 현장담당 공사일보 달력 조회.
     *
     * @param request
     * @param searchDailyParams
     * @param model
     * @return
     */
    @GetMapping(value = "/monthlyData")
    public @ResponseBody
    CamelCaseMap monthlyData(HttpServletRequest request, SearchDailyParams searchDailyParams, Model model) {
        CamelCaseMap map = new CamelCaseMap();
        searchDailyParams.setPartnerNo(getUserContext(request).getCoprcpNo());
        map.put("result", fieldService.getMonthlyData(searchDailyParams));
        return map;
    }

    /**
     * 현장담당 공사일보 해당일자 전체 데이터(그룹) 조회.
     *
     * @param searchDailyParams
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    @GetMapping(value = "/dailyAllData")
    public @ResponseBody
    CamelCaseMap dailyAllData(HttpServletRequest request, SearchDailyParams searchDailyParams, Model model) throws IOException, TemplateException {
        return fieldService.renderDailyAllDataTemplate(request, searchDailyParams);
    }

    /**
     * 현장담당 공사일보 마스터 및 일보제출 대상 저장.
     *
     * @param saveDailyFieldMasterParams
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    @PostMapping(value = "/saveDailyMasterData")
    public @ResponseBody
    CamelCaseMap saveDailyMasterData(SaveDailyFieldMasterParams saveDailyFieldMasterParams, Model model) {
        CamelCaseMap map = new CamelCaseMap();
        if (fieldService.saveDailyMasterData(saveDailyFieldMasterParams)) {
            SearchDailyParams searchDailyParams = new SearchDailyParams();
            searchDailyParams.setDeptCd(saveDailyFieldMasterParams.getMaster().getDeptCd());
            searchDailyParams.setDay(saveDailyFieldMasterParams.getMaster().getDay());
            searchDailyParams.setOrdrNo(saveDailyFieldMasterParams.getMaster().getOrdrNo());
            searchDailyParams.setPartnerNo(saveDailyFieldMasterParams.getPartnerNo());
            map.put("result", fieldService.getDailyFieldData(searchDailyParams));
        }
        return map;
    }

    /**
     * 현장담당 공사일보 협력사 데이터 조회
     *
     * @param searchDailyParams
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    @GetMapping(value = "/dailyModalData")
    public @ResponseBody
    CamelCaseMap dailyModalData(HttpServletRequest request, SearchDailyParams searchDailyParams, Model model) throws IOException, TemplateException {
        return fieldService.renderDailyModalTemplate(request, searchDailyParams);
    }

    /**
     * 현장담당 공사일보 모달 데이터 저장.
     * 모달 데이터는 한화건설 당사(협력사와 같은 개념임) 일보가 될 수 있고,
     * 협력사가 입력한 내용을 수정할 수 있다.
     *
     * @param saveDailyParams
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    @PostMapping(value = "/saveDailyModalData")
    public @ResponseBody
    CamelCaseMap saveDailyModalData(SaveDailyParams saveDailyParams, Model model) {
        CamelCaseMap map = new CamelCaseMap();

        if (fieldService.saveDailyDataEachCompany(saveDailyParams)) {
            SearchDailyParams searchDailyParams = new SearchDailyParams();
            searchDailyParams.setDeptCd(saveDailyParams.getComp().getDeptCd());
            searchDailyParams.setDay(saveDailyParams.getComp().getDay());
            searchDailyParams.setOrdrNo(saveDailyParams.getComp().getOrdrNo());
            searchDailyParams.setPartnerNo(saveDailyParams.getComp().getPartnerNo());
            searchDailyParams.setCtrcNo(saveDailyParams.getComp().getCtrcNo());
            map.put("result", fieldService.getDailyDataEachByCompany(searchDailyParams));
        }

        return map;
    }

    /**
     * 공사일보 현장담당 승인(to 협력사)
     *
     * @param saveDailyApprovalParams
     * @param model
     * @return
     */
    @PostMapping(value = "/approvalToPartner")
    public @ResponseBody
    CamelCaseMap approvalToPartner(SaveDailyApprovalParams saveDailyApprovalParams, Model model) {
        return fieldService.approvalToPartner(saveDailyApprovalParams);
    }


    /**
     * 현장담당 공사일보 히스토리 대상 데이터 조회.
     *
     * @param searchDailyParams
     * @param model
     * @return
     */
    @GetMapping(value = "/historyiesByCtrcNo")
    public @ResponseBody
    List<CamelCaseMap> historyiesByCtrcNo(SearchDailyParams searchDailyParams, Model model) {
        return fieldService.getHistories(searchDailyParams);
    }


    /**
     * 현장담당 공사일보 복사 생성 처리.
     *
     * @param historyDailyCopyParams
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    @PostMapping(value = "/copyHisotryData")
    public @ResponseBody
    CamelCaseMap copyHisotryData(HistoryDailyCopyParams historyDailyCopyParams, Model model) {
        fieldService.copyHisotryData(historyDailyCopyParams);
        CamelCaseMap map = new CamelCaseMap();
        map.put("result", fieldService.getDailyDataEachByCompany(historyDailyCopyParams.getTargetDayParams()));
        return map;
    }

    /**
     * 공사일보 결재자 찾기
     *
     * @param searchApprovalUserParams
     * @param model
     * @return
     */
    @PostMapping(value = "/approvalSearchUsers")
    public @ResponseBody
    CamelCaseMap approvalSearchUsers(SearchApprovalUserParams searchApprovalUserParams, Model model) {
        CamelCaseMap map = new CamelCaseMap();
        map.put("result", fieldService.searchApprovalUserData(searchApprovalUserParams));
        return map;
    }

    /**
     * 현장담당 공사일보 상신 또는 승인 처리.
     *
     * @param saveDailyApprovalParams
     * @param model
     * @return
     */
    @PostMapping(value = "/approvalToField")
    public @ResponseBody
    CamelCaseMap approvalToField(SaveDailyApprovalParams saveDailyApprovalParams, Model model) {
        return fieldService.approvalToField(saveDailyApprovalParams);
    }

    /**
     * 공사일보 변경작성 가능 여부 검토.
     *
     * @param searchDailyParams
     * @param model
     * @return
     */
    @GetMapping(value = "/verifyChangeData")
    public @ResponseBody
    CamelCaseMap verifyChangeData(SearchDailyParams searchDailyParams, Model model) {
        CamelCaseMap map = new CamelCaseMap();
        map.put("result", fieldService.verifyChangeData(searchDailyParams));
        return map;
    }

    /**
     * 공사일보 변경작성 생성.
     *
     * @param searchDailyParams
     * @param model
     * @return
     * @see verifyChangeData
     */
    @PostMapping(value = "/createChangeData")
    public @ResponseBody
    CamelCaseMap createChangeData(SearchDailyParams searchDailyParams, Model model) {
        CamelCaseMap map = new CamelCaseMap();
        map.put("result", fieldService.createChangeData(searchDailyParams));
        return map;
    }

    /**
     * 공사일보 변경작성 취소.
     *
     * @param searchDailyParams
     * @param model
     * @return
     */
    @PostMapping(value = "/cancelChangeData")
    public @ResponseBody
    CamelCaseMap cancelChangeData(SearchDailyParams searchDailyParams, Model model) {
        CamelCaseMap map = new CamelCaseMap();
        map.put("result", fieldService.cancelChangeData(searchDailyParams));
        return map;
    }

    /**
     * 공사일보 협력사 상태 변경이력 조회.
     *
     * @param searchDailyParams
     * @param model
     * @return
     */
    @GetMapping(value = "/getChangedCompStaHistories")
    public @ResponseBody
    List<DdBrfgCompStaHst> getChangedCompStaHistories(SearchDailyParams searchDailyParams, Model model) {
        return fieldService.getChangedCompStaHistories(searchDailyParams);
    }

    /**
     * 자동완성 조회.
     *
     * @param searchDailyParams
     * @param model
     * @return
     */
    @GetMapping(value = "/autocompleteSearch")
    public @ResponseBody
    List<CamelCaseMap> autocompleteSearch(SearchDailyParams searchDailyParams, Model model) {
        return dailyCommonService.autocompleateSearch(searchDailyParams);
    }
}
