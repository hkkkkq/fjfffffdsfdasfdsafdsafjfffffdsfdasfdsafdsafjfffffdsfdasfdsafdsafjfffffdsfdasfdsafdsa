package com.henc.cdrs.daily.web;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.daily.model.HistoryDailyCopyParams;
import com.henc.cdrs.daily.model.SaveDailyParams;
import com.henc.cdrs.daily.model.SearchDailyParams;
import com.henc.cdrs.daily.service.DailyCommonService;
import com.henc.cdrs.daily.service.PartnerService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
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
 * 협력사 공사일보 Controller.
 */
@Controller
@RequestMapping("/daily/partner")
public class PartnerController extends BaseController {

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private DailyCommonService dailyCommonService;

    /**
     * 협력사 공사일보 메인.
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {
        return "views/daily/partner";
    }

    /**
     * 협렵사 공사일보 달력 조회.
     * @param request
     * @param searchDailyParams
     * @param model
     * @return
     */
    @GetMapping(value = "/monthlyData")
    public @ResponseBody
    CamelCaseMap partnerMonthly(HttpServletRequest request, SearchDailyParams searchDailyParams, Model model) {
        CamelCaseMap map = new CamelCaseMap();
        searchDailyParams.setPartnerNo(getUserContext(request).getCoprcpNo());
        map.put("result", partnerService.getMonthlyPartnerData(searchDailyParams));
        return map;
    }

    /**
     * 협력사 공사일보 데이터 조회.
     * @param searchDailyParams
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    @GetMapping(value = "/dailyData")
    public @ResponseBody
    CamelCaseMap partnerDaily(HttpServletRequest request, SearchDailyParams searchDailyParams, Model model) throws IOException, TemplateException {
        return partnerService.renderDailyTemplate(request, searchDailyParams);
    }

    /**
     * 협력사 공사일보 작성 저장.
     * @param saveDailyParams
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    @PostMapping(value = "/savePartnerDailyData")
    public @ResponseBody
    CamelCaseMap savePartnerDailyData(SaveDailyParams saveDailyParams, Model model) {
        CamelCaseMap map = new CamelCaseMap();

        if (partnerService.saveDailyPartnerData(saveDailyParams)) {
            SearchDailyParams searchDailyParams = new SearchDailyParams();
            searchDailyParams.setDeptCd(saveDailyParams.getComp().getDeptCd());
            searchDailyParams.setDay(saveDailyParams.getComp().getDay());
            searchDailyParams.setOrdrNo(saveDailyParams.getComp().getOrdrNo());
            searchDailyParams.setPartnerNo(saveDailyParams.getComp().getPartnerNo());
            searchDailyParams.setCtrcNo(saveDailyParams.getComp().getCtrcNo());
            searchDailyParams.setCtrcRenderingPosition(saveDailyParams.getCtrcRenderingPosition());
            map.put("result", partnerService.getDailyPartnerData(searchDailyParams));
        }

        return map;
    }

    /**
     * 협력사 공사일보 히스토리 대상 데이터 조회.
     * @param searchDailyParams
     * @param model
     * @return
     */
    @GetMapping(value = "/historyiesByCtrcNo")
    public @ResponseBody
    List<CamelCaseMap> historyiesByCtrcNo(SearchDailyParams searchDailyParams, Model model) {
        return partnerService.getHistories(searchDailyParams);
    }


    /**
     * 협력사 공사일보 복사 생성 처리.
     * @param historyDailyCopyParams
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    @PostMapping(value = "/copyHisotryData")
    public @ResponseBody
    CamelCaseMap copyHisotryData(HistoryDailyCopyParams historyDailyCopyParams, Model model) {
        partnerService.copyHisotryData(historyDailyCopyParams);
        CamelCaseMap map = new CamelCaseMap();
        map.put("result", partnerService.getDailyPartnerData(historyDailyCopyParams.getTargetDayParams()));
        return map;
    }

    /**
     * 자동완성 조회.
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
