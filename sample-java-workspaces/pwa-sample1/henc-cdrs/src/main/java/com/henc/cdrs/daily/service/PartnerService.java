package com.henc.cdrs.daily.service;

import com.henc.cdrs.common.service.NotExistsException;
import com.henc.cdrs.coprcp.model.CoprcpInfo;
import com.henc.cdrs.coprcp.repository.CoprcpInfoMapper;
import com.henc.cdrs.daily.model.*;
import com.henc.cdrs.daily.repository.*;
import com.henc.cdrs.mgmt.constType.model.Ocpt;
import com.henc.cdrs.mgmt.constType.repository.ConstTypeMapper;
import com.henc.cdrs.mgmt.equipment.model.Equipment;
import com.henc.cdrs.mgmt.equipment.repository.EquipmentMapper;
import com.henc.cdrs.mgmt.matrial.model.Matrial;
import com.henc.cdrs.mgmt.matrial.repository.MatrialMapper;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.security.common.SecurityUtil;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PartnerService {

    @Autowired
    private DdBrfgCompMapper compMapper;

    @Autowired
    private DdBrfgLabatdMapper labatdMapper;

    @Autowired
    private DdBrfgEqipMapper eqipMapper;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private DdBrfgMtilMapper mtilMapper;

    @Autowired
    private MatrialMapper matrialMapper;

    @Autowired
    private DdBrfgQtyMapper qtyMapper;

    @Autowired
    private DdBrfgCommonMapper commonMapper;

    @Autowired
    private CoprcpInfoMapper coprcpInfoMapper;

    /**
     * 직종 조회를 위한 Mapper 참조
     */
    @Autowired
    private ConstTypeMapper constTypeMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private Configuration freemarker;

    @Autowired
    private DailyCommonService dailyCommonService;

    /**
     * 일일 보고 회사 정보 저장
     *
     * @param ddBrfgComp
     * @return
     */
    public DdBrfgComp saveComp(DdBrfgComp ddBrfgComp) {
        SearchDailyParams searchDailyParams = new SearchDailyParams();
        searchDailyParams.setDeptCd(ddBrfgComp.getDeptCd());
        searchDailyParams.setDay(ddBrfgComp.getDay());
        searchDailyParams.setPartnerNo(ddBrfgComp.getPartnerNo());
        searchDailyParams.setCtrcNo(ddBrfgComp.getCtrcNo());
        searchDailyParams.setOrdrNo(ddBrfgComp.getOrdrNo());

        DdBrfgComp comp = compMapper.getDaily(searchDailyParams);
        if (comp == null) {
            // 기본값 적용 후 저장
            ddBrfgComp.setSortNoSeq(ddBrfgComp.getSortNoSeq());
            ddBrfgComp.setObjtYn("Y");
            ddBrfgComp.setStaCd(DdBrfgCompStaCd.DRAFT.getValue());
            compMapper.insert(ddBrfgComp);
            dailyCommonService.createCompStaHistory(ddBrfgComp, DdBrfgStaCd.DRAFT.getName());
        } else {
            compMapper.update(ddBrfgComp);
        }
        return ddBrfgComp;
    }

    /**
     * 출역 인원 저장
     *
     * @param labatds
     */
    public void saveLabatd(List<DdBrfgLabatd> labatds) {
        if (labatds != null) {
            for (DdBrfgLabatd labatd : labatds) {
                switch (labatd.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        if (labatd.getLabatdPcntNo() == null) {
                            labatdMapper.insertLabatdPcnt(labatd);
                        }
                        labatdMapper.insert(labatd);
                        break;

                    case IBSheetRowStatus.UPDATED:
                        labatdMapper.updateLabatdPcnt(labatd);
                        labatdMapper.update(labatd);
                        break;

                    case IBSheetRowStatus.DELETED:
                        labatdMapper.delete(labatd);
                        break;

                    default:
                        throw new UnsupportedRowTypeException(labatd.getRowStatus());
                }
            }
        }
    }

    /**
     * 자재 저장
     *
     * @param mtils
     */
    public void saveMtil(List<DdBrfgMtil> mtils) {
        if (mtils != null) {
            for (DdBrfgMtil mtil : mtils) {
                switch (mtil.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        if (mtil.getMtilNo() == null) {
                            Matrial matrial = new Matrial();
                            matrial.setBizhdofCd(mtil.getBizhdofCd());
                            matrial.setMtilNm(mtil.getMtilNm());
                            matrial.setSpec(mtil.getSpec());
                            matrial.setUnit(mtil.getUnit());
                            CamelCaseMap matrialMap = matrialMapper.getMatrial(matrial);
                            if (matrialMap == null) {
                                matrial.setApbtYn("N");
                                matrial.setUseYn("Y");
                                matrialMapper.insertMatrial(matrial);
                                mtil.setMtilNo(matrial.getMtilNo());
                            } else {
                                mtil.setMtilNo(matrialMap.getInt("mtilNo"));
                            }
                        }
                        mtilMapper.insert(mtil);
                        break;

                    case IBSheetRowStatus.UPDATED:
                        mtilMapper.update(mtil);
                        break;

                    case IBSheetRowStatus.DELETED:
                        mtilMapper.delete(mtil);
                        break;

                    default:
                        throw new UnsupportedRowTypeException(mtil.getRowStatus());
                }
            }
        }
    }

    /**
     * 장비 저장
     *
     * @param eqips
     */
    public void saveEqip(List<DdBrfgEqip> eqips) {
        if (eqips != null) {
            for (DdBrfgEqip eqip : eqips) {
                switch (eqip.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        if (eqip.getEqipNo() == null) {
                            Equipment equipment = new Equipment();
                            equipment.setBizhdofCd(eqip.getBizhdofCd());
                            equipment.setEqipNm(eqip.getEqipNm());
                            equipment.setSpec(eqip.getSpec());
                            equipment.setUnit(eqip.getUnit());
                            CamelCaseMap eqipMap = equipmentMapper.getEquipment(equipment);
                            if (eqipMap == null) {
                                equipment.setApbtYn("N");
                                equipment.setUseYn("Y");
                                equipmentMapper.insertEquipment(equipment);
                                eqip.setEqipNo(equipment.getEqipNo());
                            } else {
                                eqip.setEqipNo(eqipMap.getInt("eqipNo"));
                            }
                        }
                        eqipMapper.insert(eqip);
                        break;

                    case IBSheetRowStatus.UPDATED:
                        eqipMapper.update(eqip);
                        break;

                    case IBSheetRowStatus.DELETED:
                        eqipMapper.delete(eqip);
                        break;

                    default:
                        throw new UnsupportedRowTypeException(eqip.getRowStatus());
                }
            }
        }
    }

    /**
     * 물량 저장
     *
     * @param qties
     */
    public void saveQty(List<DdBrfgQty> qties) {
        if (qties != null) {
            for (DdBrfgQty qty : qties) {
                switch (qty.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        if (qty.getQtyNo() == null) {
                            qtyMapper.insertCnstQty(qty);
                        }
                        qtyMapper.insert(qty);
                        break;

                    case IBSheetRowStatus.UPDATED:
                        qtyMapper.update(qty);
                        break;

                    case IBSheetRowStatus.DELETED:
                        qtyMapper.delete(qty);
                        break;

                    default:
                        throw new UnsupportedRowTypeException(qty.getRowStatus());
                }
            }
        }
    }

    /**
     * 해당 일자의 공사일보 데이터 조회.
     *
     * @param searchDailyParams
     * @return
     */
    public Map getDailyPartnerData(SearchDailyParams searchDailyParams) {
        Map<String, Object> data = new HashMap<>();
        UserDetail userDetail = SecurityUtil.getUserContext(request);

        // 일일 보고 마스터 정보(조회 후 없으면 차수생성)
        DdBrfg ddBrfg = dailyCommonService.topGetMaster(searchDailyParams);
        data.put("master", ddBrfg);

        List<Map<String, Object>> ctrcList = new ArrayList<>();

        CoprcpInfo coprcpInfo = new CoprcpInfo();
        coprcpInfo.setDeptCd(searchDailyParams.getDeptCd());
        coprcpInfo.setCoprcpNo(searchDailyParams.getPartnerNo());
        List<CamelCaseMap> ctrcs = coprcpInfoMapper.getCtrcs(coprcpInfo);

        for (CamelCaseMap ctrc : ctrcs) {
            searchDailyParams.setCtrcNo(ctrc.getString("ctrcNo"));

            Map<String, Object> ctrcData = new HashMap<>();
            ctrcData.put("ctrc", ctrc);

            // 일일 보고 회사 정보
            // 신규일 경우 comp가 Null 일 수 있음
            DdBrfgComp comp = compMapper.getDaily(searchDailyParams);
            if (comp == null) {
                comp = new DdBrfgComp(searchDailyParams);
                // 기본값 적용
                comp.setStaCd("00");
                comp.setObjtYn("Y");
                comp.setSortNoSeq(ctrc.getInt("sortNo"));
            }
            ctrcData.put("comp", comp);

            // 일일 보고 출역 정보
            List<DdBrfgLabatd> ddBrfgLabatds = labatdMapper.dailyList(searchDailyParams);
            ctrcData.put("labatds", ddBrfgLabatds);

            // 일일 보고 자재 정보
            List<DdBrfgMtil> ddBrfgMtils = mtilMapper.dailyList(searchDailyParams);
            ctrcData.put("mtils", ddBrfgMtils);

            // 일일 보고 장비 정보
            List<DdBrfgEqip> ddBrfgEqips = eqipMapper.dailyList(searchDailyParams);
            ctrcData.put("eqips", ddBrfgEqips);

            // 일일 보고 물량 정보
            List<DdBrfgQty> ddBrfgQties = qtyMapper.dailyList(searchDailyParams);
            ctrcData.put("qties", ddBrfgQties);

            // 사용가능 직종 정보(공종은 계약에 속한 공종에 귀속된다).
            Ocpt ocpt = new Ocpt();
            ocpt.setBizHdofCd(userDetail.getBizhdofCd());
            ocpt.setCstkndNo(ctrc.getInt("cstkndNo"));
            ctrcData.put("ocpts", constTypeMapper.selectOcptGrdList(ocpt));

            // 맵 리스트 추가
            ctrcList.add(ctrcData);
        }
        data.put("ctrcData", ctrcList);

        // 첫 번째 계약이 active 되게 한다.
        searchDailyParams.setCtrcNo(ctrcs.get(0).getString("ctrcNo"));
        data.put("activeCtrcNo", searchDailyParams.getCtrcNo());
        return data;
    }

    /**
     * 공사일보 마스터 데이터 조회(달력을 기준으로 차수와 상태 정보를 조회).
     *
     * @param searchDailyParams
     * @return
     */
    public Map getMonthlyPartnerData(SearchDailyParams searchDailyParams) {
        Map<String, Object> data = new HashMap<>();
        List<DdBrfg> ddBrfgs = commonMapper.monthlyCalendar(searchDailyParams);
        data.put("calendarData", ddBrfgs);

        List<CamelCaseMap> comps = commonMapper.monthlyPartnerComp(searchDailyParams);

        Map<String, Object> partnerData = new HashMap<>();
        for (CamelCaseMap comp : comps) {
            ArrayList arrayList = new ArrayList();

            if (partnerData.containsKey(comp.getString("day"))) {
                arrayList = (ArrayList<DdBrfgComp>) partnerData.get(comp.getString("day"));
            }
            arrayList.add(comp);
            partnerData.put(comp.getString("day"), arrayList);
        }
        data.put("partnerData", partnerData);

        return data;
    }

    /**
     * 공사일보 협력사 저장
     *
     * @param dailySaveParams
     * @return
     */
    public boolean saveDailyPartnerData(SaveDailyParams saveDailyParams) {
        // 주요업무
        DdBrfgComp comp = saveDailyParams.getComp();
        if (comp == null) {
            throw new NotExistsException("주요업무 정보가 존재하지 않습니다.");
        }

        // 회사정보
        saveComp(comp);
        // 출역인원
        saveLabatd(saveDailyParams.getLabatds());
        // 주요자재
        saveMtil(saveDailyParams.getMtils());
        // 주요장비
        saveEqip(saveDailyParams.getEqips());
        // 공사물량
        saveQty(saveDailyParams.getQties());

        // 일보 제출
        if (comp.getStaCd().equals(DdBrfgCompStaCd.APPROVAL.getValue())) {
            dailyCommonService.createCompStaHistory(comp, DdBrfgCompStaCd.APPROVAL.getName());
        }

        return true;
    }

    /**
     * 일보 작성 히스토리 데이터 조회(과거 7일).
     *
     * @param searchDailyParams
     * @return
     */
    public List<CamelCaseMap> getHistories(SearchDailyParams searchDailyParams) {
        return commonMapper.historyiesByCtrcNo(searchDailyParams);
    }


    /**
     * 일보 히스토리 복사 생성.
     * 현재 일자 파라미터의 데이터를 조회하여 삭제하고,
     * 복사 일자 파라미터의 데이터를 조회하여 생성한다.
     *
     * @param historyDailyCopyParams
     * @return
     */
    public boolean copyHisotryData(HistoryDailyCopyParams historyDailyCopyParams) {
        // 공사물량 현재 일자 삭제
        qtyMapper.deleteAll(historyDailyCopyParams.getTargetDayParams());
        // 주요자재 현재 일자 삭제
        mtilMapper.deleteAll(historyDailyCopyParams.getTargetDayParams());
        // 주요장비 현재 일자 삭제
        eqipMapper.deleteAll(historyDailyCopyParams.getTargetDayParams());
        // 출역 현재 일자 삭제
        labatdMapper.deleteAll(historyDailyCopyParams.getTargetDayParams());

        // 일보 회사 복사본 저장
        DdBrfgComp targetComp = compMapper.getDaily(historyDailyCopyParams.getTargetDayParams());
        if (targetComp == null) {
            DdBrfgComp comp = new DdBrfgComp(historyDailyCopyParams.getTargetDayParams());
            // 일보 제출대상 기본설정
            comp.setObjtYn("Y");
            // 일보 정렬순번 기본설정
            CoprcpInfo coprcpInfo = new CoprcpInfo();
            coprcpInfo.setDeptCd(historyDailyCopyParams.getTargetDayParams().getDeptCd());
            coprcpInfo.setCoprcpNo(historyDailyCopyParams.getTargetDayParams().getPartnerNo());
            List<CamelCaseMap> ctrcs = coprcpInfoMapper.getCtrcs(coprcpInfo);
            for (CamelCaseMap ctrc : ctrcs) {
                if (comp.getCtrcNo().equals(ctrc.getString("ctrcNo"))) {
                    comp.setSortNoSeq(ctrc.getInt("sortNoSeq"));
                    break;
                }
            }
            compMapper.insert(comp);
        }

        DdBrfgComp comp = compMapper.getDaily(historyDailyCopyParams.getCopyDayParams());
        comp.setStaCd(historyDailyCopyParams.getTargetDayParams().getStaCd());
        comp.setDay(historyDailyCopyParams.getTargetDayParams().getDay());
        compMapper.copyHistoryUpdate(comp);

        // 출역 복사 생성
        labatdMapper.copyHistoryCreate(historyDailyCopyParams);
        // 주요장비 복사 생성
        eqipMapper.copyHistoryCreate(historyDailyCopyParams);
        // 주요자재 복사 생성
        mtilMapper.copyHistoryCreate(historyDailyCopyParams);
        // 공사물량 복사 생성
        qtyMapper.copyHistoryCreate(historyDailyCopyParams);

        return true;
    }

    /**
     * 일보 템플릿 렌더링.
     *
     * @param searchDailyParams
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public CamelCaseMap renderDailyTemplate(HttpServletRequest request, SearchDailyParams searchDailyParams) throws IOException, TemplateException {
        CamelCaseMap map = new CamelCaseMap();
        map.put("result", getDailyPartnerData(searchDailyParams));

        UserDetail userDetail = SecurityUtil.getUserContext(request);
        map.put("main_bizhdof_cd", userDetail.getBizhdofCd());

        Template template = freemarker.getTemplate("daily/dailyPartner.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        map.put("html", text);
        return map;
    }

}