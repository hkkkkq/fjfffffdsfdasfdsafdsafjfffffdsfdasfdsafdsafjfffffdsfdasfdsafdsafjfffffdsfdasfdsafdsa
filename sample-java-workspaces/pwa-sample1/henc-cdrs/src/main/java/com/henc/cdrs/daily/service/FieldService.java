package com.henc.cdrs.daily.service;

import com.henc.cdrs.common.code.service.CodeService;
import com.henc.cdrs.common.notification.model.PushHst;
import com.henc.cdrs.common.notification.service.NotificationService;
import com.henc.cdrs.common.service.NotExistsException;
import com.henc.cdrs.coprcp.model.CoprcpInfo;
import com.henc.cdrs.coprcp.repository.CoprcpInfoMapper;
import com.henc.cdrs.daily.model.*;
import com.henc.cdrs.daily.repository.*;
import com.henc.cdrs.exception.DailyFieldException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class FieldService {

    @Autowired
    private DdBrfgMapper masterMapper;

    @Autowired
    private DdBrfgAprvMapper aprvMapper;

    @Autowired
    private DdBrfgStaHstMapper staHstMapper;

    @Autowired
    private DdBrfgCompMapper compMapper;

    @Autowired
    private DdBrfgCompStaHstMapper compStaHstMapper;

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

    @Autowired
    private DdBrfgChgMtrMapper changeMapper;

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

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CodeService codeService;


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
            dailyCommonService.createCompStaHistory(ddBrfgComp, DdBrfgCompStaCd.DRAFT.getName());
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
     * 현장담당이 조회 하므로 해당일자에 속한 모든 계약데이터를 협력사별로 조회한다.
     * dept_cd, day, ordr_no 에 속한 모든 데이트를 의미하고,
     * 한화건설 계약분도 표시하여 현장담당이 입력할 수 있어야 한다.
     *
     * @param searchDailyParams
     * @return
     */
    public Map getDailyFieldData(SearchDailyParams searchDailyParams) {
        Map<String, Object> data = new HashMap<>();
        UserDetail userDetail = SecurityUtil.getUserContext(request);

        // 일일 보고 마스터 정보(조회 후 없으면 차수생성)
        DdBrfg ddBrfg = dailyCommonService.topGetMaster(searchDailyParams);
        data.put("master", ddBrfg);

        // 현재 로그인한 사용자가 해당 공사일보의 데이터를 승인할 사용자인지 판단한다.
        data.put("isSignerSeq", dailyCommonService.isAprvSigner(ddBrfg));

        data.put("aprvUsers", dailyCommonService.aprvUsers(ddBrfg));

        CoprcpInfo coprcpInfo = new CoprcpInfo();
        coprcpInfo.setDeptCd(searchDailyParams.getDeptCd());
        coprcpInfo.setCoprcpNo(searchDailyParams.getPartnerNo());
        List<CamelCaseMap> ctrcs = coprcpInfoMapper.getCtrcs(coprcpInfo);

        // 한화건설 당사 협력업체 계약정보는 현장에 무조건 1건이어야 한다.
        if (ctrcs.size() != 1) {
            throw new DailyFieldException("한화건설 당사 계약정보가 존재하지 않습니다.");
        }
        CamelCaseMap ctrc = ctrcs.get(0);
        data.put("ctrc", ctrc);

        // 해당 일자로 작성된 한화건설 현장 일보가 존재하지 않을 경우 생성
        SearchDailyParams hencSearchDailyParams = searchDailyParams.copy();
        hencSearchDailyParams.setCtrcNo(ctrc.getString("ctrcNo"));
        DdBrfgComp hencComp = compMapper.getDaily(hencSearchDailyParams);
        if (hencComp == null) {
            hencComp = new DdBrfgComp(hencSearchDailyParams);
            hencComp.setOrdrNo(ddBrfg.getOrdrNo());
            hencComp.setSortNoSeq(0);//한화건설 0순위 표시
            compMapper.insert(hencComp);
            dailyCommonService.createCompStaHistory(hencComp, DdBrfgCompStaCd.DRAFT.getName());
        }

        // 일일 보고 회사 정보
        data.put("comp", compMapper.dailyGroupList(searchDailyParams));

        // 일일 보고 출역 정보
        data.put("labatds_henc", labatdMapper.dailyGroupListForHenc(searchDailyParams));
        data.put("labatds", labatdMapper.dailyGroupList(searchDailyParams));

        // 일일 보고 자재 정보
        data.put("mtils", mtilMapper.dailyGroupList(searchDailyParams));

        // 일일 보고 장비 정보
        data.put("eqips", eqipMapper.dailyGroupList(searchDailyParams));

        // 일일 보고 물량 정보
        data.put("qties", qtyMapper.dailyGroupList(searchDailyParams));

        // 사용가능 직종 정보(공종은 계약에 속한 공종에 귀속된다).
        Ocpt ocpt = new Ocpt();
        ocpt.setBizHdofCd(userDetail.getBizhdofCd());
        ocpt.setCstkndNo(ctrc.getInt("cstkndNo"));
        data.put("ocpts", constTypeMapper.selectOcptGrdList(ocpt));

        // 주요 출역,장비,자재,물량 퀵서치 리스트
        data.put("quickLabatds", labatdMapper.quickSearchList(searchDailyParams));
        data.put("quickEqips", eqipMapper.quickSearchList(searchDailyParams));
        data.put("quickMtils", mtilMapper.quickSearchList(searchDailyParams));
        data.put("quickQties", qtyMapper.quickSearchList(searchDailyParams));

        // 해당 현장에 결재중인 데이터가 존재하는지 확인
        data.put("staCdCount", masterMapper.getStaCdCount(searchDailyParams.getDeptCd(), DdBrfgStaCd.APPROVAL.getValue()));

        return data;
    }

    /**
     * 해당 일자의 공사일보를 협력사 별로 조회 한다.
     *
     * @param searchDailyParams
     * @return
     */
    public Map getDailyDataEachByCompany(SearchDailyParams searchDailyParams) {
        Map<String, Object> data = new HashMap<>();
        UserDetail userDetail = SecurityUtil.getUserContext(request);

        // 일일 보고 마스터 정보(조회 후 없으면 차수생성)
        DdBrfg ddBrfg = dailyCommonService.topGetMaster(searchDailyParams);
        data.put("master", ddBrfg);

        // 협력사 계약장보 조회
        CoprcpInfo coprcpInfo = new CoprcpInfo();
        coprcpInfo.setDeptCd(searchDailyParams.getDeptCd());
        coprcpInfo.setCoprcpNo(searchDailyParams.getPartnerNo());
        coprcpInfo.setCtrcNo(searchDailyParams.getCtrcNo());
        CamelCaseMap ctrc = coprcpInfoMapper.getCtrc(coprcpInfo);
        if (ctrc == null) {
            throw new DailyFieldException("협력사 계약정보가 존재하지 않습니다.");
        }
        data.put("ctrc", ctrc);

        // 일일 보고 회사 정보
        DdBrfgComp comp = compMapper.getDaily(searchDailyParams);
        if (comp == null) {
            comp = new DdBrfgComp(searchDailyParams);
            // 기본값 적용
            comp.setStaCd("00");
            comp.setObjtYn("Y");
            comp.setSortNoSeq(ctrc.getInt("sortNo"));
        }
        data.put("comp", comp);

        // 일일 보고 출역 정보
        data.put("labatds", labatdMapper.dailyList(searchDailyParams));

        // 일일 보고 자재 정보
        data.put("mtils", mtilMapper.dailyList(searchDailyParams));

        // 일일 보고 장비 정보
        data.put("eqips", eqipMapper.dailyList(searchDailyParams));

        // 일일 보고 물량 정보
        data.put("qties", qtyMapper.dailyList(searchDailyParams));

        // 사용가능 직종 정보(공종은 계약에 속한 공종에 귀속된다).
        Ocpt ocpt = new Ocpt();
        ocpt.setBizHdofCd(userDetail.getBizhdofCd());
        ocpt.setCstkndNo(ctrc.getInt("cstkndNo"));
        data.put("ocpts", constTypeMapper.selectOcptGrdList(ocpt));

        // 주요 출역,장비,자재,물량 퀵서치 리스트
        data.put("quickLabatds", labatdMapper.quickSearchList(searchDailyParams));
        data.put("quickEqips", eqipMapper.quickSearchList(searchDailyParams));
        data.put("quickMtils", mtilMapper.quickSearchList(searchDailyParams));
        data.put("quickQties", qtyMapper.quickSearchList(searchDailyParams));

        return data;
    }

    /**
     * 공사일보 마스터 데이터 조회(달력을 기준으로 차수와 상태 정보를 조회).
     *
     * @param searchDailyParams
     * @return
     */
    public Map getMonthlyData(SearchDailyParams searchDailyParams) {
        Map<String, Object> data = new HashMap<>();
        List<DdBrfg> ddBrfgs = commonMapper.monthlyCalendar(searchDailyParams);
        data.put("calendarData", ddBrfgs);
        return data;
    }

    /**
     * 공사일보 현장담당 공통사항 및 일보제출 여부 체크 항목 저장.
     *
     * @param saveDailyFieldMasterParams
     * @return
     */
    public boolean saveDailyMasterData(SaveDailyFieldMasterParams saveDailyFieldMasterParams) {
        masterMapper.update(saveDailyFieldMasterParams.getMaster());

        if (saveDailyFieldMasterParams.getComps() != null) {
            for (DdBrfgComp comp : saveDailyFieldMasterParams.getComps()) {
                SearchDailyParams searchDailyParams = new SearchDailyParams();
                searchDailyParams.setDeptCd(comp.getDeptCd());
                searchDailyParams.setDay(comp.getDay());
                searchDailyParams.setOrdrNo(comp.getOrdrNo());
                searchDailyParams.setCtrcNo(comp.getCtrcNo());
                searchDailyParams.setPartnerNo(comp.getPartnerNo());
                DdBrfgComp ddBrfgComp = compMapper.getDaily(searchDailyParams);
                if (DdBrfgCompStaCd.NONE.getValue().equals(comp.getStaCd()) && ddBrfgComp == null) {
                    compMapper.insert(comp);
                } else {
                    compMapper.updateObjectYN(comp);
                }
            }
        }
        return true;
    }

    /**
     * 공사일보 현장담당 협력사 데이터 수정 및 당사계약분 정보 저장(협력사별로 입력한 데이터).
     *
     * @param dailySaveParams
     * @return
     */
    public boolean saveDailyDataEachCompany(SaveDailyParams saveDailyParams) {
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

        // 일보 데이터의 액션이 저장(save)일 때 에만 저장 히스토리를 생성한다.
        if (saveDailyParams.getAction().equalsIgnoreCase("save")) {
            if (!dailyCommonService.createCompStaHistory(comp, saveDailyParams.getChgRsn())) {
                throw new DailyFieldException("공사일보 저장 사유를 처리할 수 없습니다.");
            }

        } else {
            // 일보 데이터의 액션이 제출 또는 승인과 같은 결제일 때 에만 저장 히스토리를 생성한다.
            if (comp.getStaCd().equals(DdBrfgCompStaCd.APPROVAL.getValue())) {
                if (!dailyCommonService.createCompStaHistory(comp, DdBrfgCompStaCd.APPROVAL.getName())) {
                    throw new DailyFieldException("공사일보 제출을 처리할 수 없습니다.");
                }
            }
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
     * 일보 현장담당 협력사 Summary 템플릿 렌더링.
     *
     * @param searchDailyParams
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public CamelCaseMap renderDailyAllDataTemplate(HttpServletRequest request, SearchDailyParams searchDailyParams) throws IOException, TemplateException {
        CamelCaseMap map = new CamelCaseMap();
        map.put("result", getDailyFieldData(searchDailyParams));

        UserDetail userDetail = SecurityUtil.getUserContext(request);
        map.put("main_bizhdof_cd", userDetail.getBizhdofCd());

        map.put("weather_cd", codeService.getCodeList("WEATHER_CD"));

        Template template = freemarker.getTemplate("daily/dailyField.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        map.put("html", text);

        return map;
    }

    /**
     * 일보 현장담당 모달 템플릿 렌더링
     *
     * @param searchDailyParams
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public CamelCaseMap renderDailyModalTemplate(HttpServletRequest request, SearchDailyParams searchDailyParams) throws IOException, TemplateException {
        CamelCaseMap map = new CamelCaseMap();
        map.put("result", getDailyDataEachByCompany(searchDailyParams));

        UserDetail userDetail = SecurityUtil.getUserContext(request);
        map.put("main_bizhdof_cd", userDetail.getBizhdofCd());

        Template template = freemarker.getTemplate("daily/dailyField_modal.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        map.put("html", text);
        return map;
    }

    /**
     * 공사일보 협력사 결제 처리.
     * 현장담당이 해당 협력사의 일보를 승인, 반려, 승인취소를 처리한다.
     *
     * @param rejectToPartnerParams
     * @return
     */
    public CamelCaseMap approvalToPartner(SaveDailyApprovalParams saveDailyApprovalParams) {
        CamelCaseMap map = new CamelCaseMap();
        compMapper.saveApproval(saveDailyApprovalParams);
        DdBrfgComp comp = new DdBrfgComp(saveDailyApprovalParams);
        if (!dailyCommonService.createCompStaHistory(comp, saveDailyApprovalParams.getRsn())) {
            throw new DailyFieldException("공사일보 상태변경 이력 생성이 실패하였습니다.");
        }

        // Push.
        // 반려할 때 Push 한다.
        if (saveDailyApprovalParams.getStaCd().equals(DdBrfgCompStaCd.REJECT.getValue())) {
            PushHst pushHst = new PushHst();
            String title = "";
            String body = "";
            String recUserId = "";

            // todo-sunyoupk 협력사 직원 반려 push (Test)
            title = "협력사 일보 반려";
            body = saveDailyApprovalParams.getLoginName() + "님이 공사일보를 반려 하였습니다.";

            // 협력사 일보 제출자 조회.
            DdBrfgCompStaHst latestSubmitter = compStaHstMapper.latestSubmitter(comp);
            if (latestSubmitter != null) {
                recUserId = latestSubmitter.getChgUpdtId();

                try {
                    pushHst.setPushTitl(title);
                    pushHst.setPushCntt(body);
                    pushHst.setReceiverId(recUserId);
                    pushHst.setPushLink("/");
                    pushHst.setDeptCd(saveDailyApprovalParams.getDeptCd());
                    pushHst.setDay(saveDailyApprovalParams.getDay());
                    pushHst.setOrderNo(saveDailyApprovalParams.getOrdrNo());
                    pushHst.setPartnerNo(saveDailyApprovalParams.getPartnerNo());
                    pushHst.setCtrcNo(saveDailyApprovalParams.getCtrcNo());

                    notificationService.pushByUser(pushHst);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        map.put("result", true);
        return map;
    }

    /**
     * 공사일보 현장담당 결재 처리.
     * 현장담당 간의 상신, 승인, 반려 처리를 한다.
     * - 1. 결재상태 저장
     * - 2. 상태 이력 생성
     * - 3. 일보 결재 생성
     * - 4. 만일 변경작성 건의 최종 승인인 경우 hold 데이터도 모두 승인으로 변경한다.
     *
     * @param saveDailyApprovalParams
     * @return
     */
    public CamelCaseMap approvalToField(SaveDailyApprovalParams saveDailyApprovalParams) {
        CamelCaseMap map = new CamelCaseMap();
        // 1. 일보 마스터 정보의 상태 코드를 변경한다.
        masterMapper.saveApproval(saveDailyApprovalParams);

        // 2. 상태 이력을 생성한다.
        DdBrfg master = new DdBrfg(saveDailyApprovalParams);
        if (!dailyCommonService.createStaHistory(master, saveDailyApprovalParams.getRsn())) {
            throw new DailyFieldException("공사일보 현장담당 상태변경 이력 생성이 실패하였습니다.");
        }

        if (saveDailyApprovalParams.getStaCd().equals(DdBrfgStaCd.APPROVAL.getValue())) {

            if (saveDailyApprovalParams.getOrdrNo() > 1) {
                // 변경된 데이터 모두 결재 데이터를 생성한다.
                for (DdBrfg changedMaster : masterMapper.getChanged(saveDailyApprovalParams)) {
                    SaveDailyApprovalParams approvalParams = new SaveDailyApprovalParams(changedMaster);
                    approvalParams.setStaCd(DdBrfgStaCd.APPROVAL.getValue());
                    approvalParams.setEmpeNo(saveDailyApprovalParams.getEmpeNo());
                    approvalParams.setAprvDt(saveDailyApprovalParams.getAprvDt());
                    approvalParams.setRsn(saveDailyApprovalParams.getRsn());
                    approval(approvalParams, (changedMaster.getChgSeq() == saveDailyApprovalParams.getChgSeq() ? master : null));
                }

                // 일보 변경 사항 내역의 상태 처리.
                // 변경 된 데이터 전부가 아닌 해당 건만 처리한다(모두 바꾸면 어떤 데이터를 근거로 Hold가 된 것인지 차후에 판단하기 어렵기 때문).
                DdBrfgChgMtr chgMtr = new DdBrfgChgMtr(saveDailyApprovalParams);
                changeMapper.update(chgMtr);

            } else {
                approval(saveDailyApprovalParams, master);
            }

        } else {
            if (saveDailyApprovalParams.getOrdrNo() > 1) {
                for (DdBrfg changedMaster : masterMapper.getChanged(saveDailyApprovalParams)) {
                    SaveDailyApprovalParams approvalParams = new SaveDailyApprovalParams(changedMaster);
                    approvalParams.setStaCd(saveDailyApprovalParams.getStaCd());
                    approvalParams.setEmpeNo(saveDailyApprovalParams.getEmpeNo());
                    approvalParams.setAprvDt(saveDailyApprovalParams.getAprvDt());
                    approvalParams.setRsn(saveDailyApprovalParams.getRsn());
                    approvalParams.setSeq(saveDailyApprovalParams.getSeq());

                    // 결재정보를(상태 코드와 결재 일자) 업데이트 한다.
                    DdBrfgAprv selfAprv = new DdBrfgAprv(approvalParams);
                    aprvMapper.saveApproval(selfAprv);

                    // Hold 된 데이터의 일보 상태를 승인처리 한다(자신의 seq는 제외하고 1번 로직에서 처리했기 때문).
                    if (changedMaster.getChgSeq() != saveDailyApprovalParams.getChgSeq() && saveDailyApprovalParams.getStaCd().equals(DdBrfgStaCd.APPROVE.getValue())) {
                        masterMapper.saveApproval(approvalParams);
                    }
                }

                // 일보 변경 사항 내역의 상태 처리.
                // 변경 된 데이터 전부가 아닌 해당 건만 처리한다(모두 바꾸면 어떤 데이터를 근거로 Hold가 된 것인지 차후에 판단하기 어렵기 때문).
                DdBrfgChgMtr chgMtr = new DdBrfgChgMtr(saveDailyApprovalParams);
                changeMapper.update(chgMtr);

            } else {
                // 자신의 결재정보를(상태 코드와 결재 일자) 업데이트 한다.
                DdBrfgAprv selfAprv = new DdBrfgAprv(saveDailyApprovalParams);
                aprvMapper.saveApproval(selfAprv);
            }
        }

        // Push.
        {
            DdBrfgAprv selfAprv = new DdBrfgAprv(saveDailyApprovalParams);
            PushHst pushHst = new PushHst();
            String title = "";
            String body = "";
            String recUserId = "";

            if (saveDailyApprovalParams.getStaCd().equals(DdBrfgStaCd.APPROVAL.getValue())) {
                // todo-sunyoupk 상신(결재요청) push (Test)
                title = "일보 결재요청";
                body = saveDailyApprovalParams.getLoginName() + "님이 공사일보를 결재요청 하였습니다.";

                // 다음 결재자 조회(최초 상신자일 경우 -1에 대한 처리)
                if (selfAprv.getSeq() < 1) {
                    selfAprv.setSeq(1);
                }
                DdBrfgAprv nextAprv = aprvMapper.getNextSigner(selfAprv);
                recUserId = nextAprv.getEmpeNo();

            } else if (saveDailyApprovalParams.getStaCd().equals(DdBrfgStaCd.APPROVE.getValue())) {
                // todo-sunyoupk 승인 push (Test)
                title = "일보 승인";
                body = saveDailyApprovalParams.getLoginName() + "님이 공사일보를 승인 하였습니다.";

                // 최초 결재 상신자 조회
                DdBrfgAprv firstAprv = aprvMapper.getFirstSigner(selfAprv);
                recUserId = firstAprv.getEmpeNo();

            } else if (saveDailyApprovalParams.getStaCd().equals(DdBrfgStaCd.REJECT.getValue())) {
                // todo-sunyoupk 반려 push (Test)
                title = "일보 반려";
                body = saveDailyApprovalParams.getLoginName() + "님이 공사일보를 반려 하였습니다.";

                // 최초 결재 상신자 조회
                DdBrfgAprv firstAprv = aprvMapper.getFirstSigner(selfAprv);
                recUserId = firstAprv.getEmpeNo();
            }

            try {
                pushHst.setPushTitl(title);
                pushHst.setPushCntt(body);
                pushHst.setReceiverId(recUserId);
                pushHst.setPushLink("/");
                pushHst.setDeptCd(saveDailyApprovalParams.getDeptCd());
                pushHst.setDay(saveDailyApprovalParams.getDay());
                pushHst.setOrderNo(saveDailyApprovalParams.getOrdrNo());
                pushHst.setPartnerNo(saveDailyApprovalParams.getPartnerNo());
                pushHst.setCtrcNo(saveDailyApprovalParams.getCtrcNo());

                notificationService.pushByUser(pushHst);

            } catch (InterruptedException e) {
                e.printStackTrace();

            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        map.put("aprv_users", dailyCommonService.aprvUsers(master));
        map.put("result", true);
        return map;
    }

    private boolean approval(SaveDailyApprovalParams saveDailyApprovalParams, DdBrfg master) {
        // 3. 상신일 경우 APRV 데이터 삭제 후, 상신 정보와 차상위자의 결재예정 정보를 생성한다.
        DdBrfgAprv selfAprv = new DdBrfgAprv(saveDailyApprovalParams);
        // 3-1. 상신자 이며 APRV 데이터 삭제 후 진행.
        if (selfAprv.getSeq() == null || selfAprv.getSeq() < 1) {
            if (master != null) {
                // 온도정보 업데이트
                masterMapper.update(master);
            }
            aprvMapper.deletePastData(selfAprv);
            // 3-2. 상신자의 경우 자신의 결재정보를 생성한다.
            selfAprv.setEmpeNo(selfAprv.getLoginId());
            aprvMapper.insert(selfAprv);
        } else {
            // 3-3. 결재자의 경우 자신의 결재정보를 반영한다(결재일자, 상태코드).
            aprvMapper.saveApproval(selfAprv);
        }

        // 3-4. 차상위 결재자 정보를 생성한다.
        DdBrfgAprv nextAprv = new DdBrfgAprv(saveDailyApprovalParams);
        nextAprv.setEmpeNo(saveDailyApprovalParams.getEmpeNo());
        nextAprv.setStaCd(null);
        nextAprv.setAprvDt(null);
        aprvMapper.insert(nextAprv);
        return true;
    }

    /**
     * 결재자 찾기 데이터 조회.
     *
     * @param searchApprovalUserParams
     * @return
     */
    public Map searchApprovalUserData(SearchApprovalUserParams searchApprovalUserParams) {
        Map map = new HashMap();
        map.put("total", commonMapper.getApprovalUserSearchTotal(searchApprovalUserParams));
        map.put("list", commonMapper.getApprovalUserSearchList(searchApprovalUserParams));
        return map;
    }

    /**
     * 공사일보 변경작성 가능성 검토.
     *
     * @param searchDailyParams
     * @return
     */
    public Map verifyChangeData(SearchDailyParams searchDailyParams) {
        Map map = new HashMap();

        // todo-sunyoupk 변경작성 rule 정의필요
        // 해당일자보다 이전 일자에 승인되지 않은 데이터(작성중은 제외)가 존재하면 생성할 수 없다.

        // 변경작성 대상의 범위 설정(해당일자 부터 마지막 승인된 데이터의 일보까지).
        List<DdBrfg> changeList = masterMapper.changeAvailiableMasterList(searchDailyParams);

        if (changeList.size() > 0) {
            map.put("verify", true);
            map.put("startDay", changeList.get(0).getDay());
            map.put("endDay", changeList.get(changeList.size() - 1).getDay());
            map.put("changeList", changeList);

        } else {
            map.put("verify", false);
        }

        return map;
    }

    /**
     * 공사일보 변경 작성 데이터 생성.
     * 1. 변경범위 조회
     * 2. 변경번호 채번
     * 3. 일보 변경 생성
     * 4. 일보 생성
     * 5. 일보 이력 생성
     * 6. 일보 회사 생성
     * 7. 일보 회사 이력 생성
     * 8. 일보 출역 생성
     * 9. 일보 자재 생성
     * 10. 일보 장비 생성
     * 11. 일보 물량 생성
     *
     * @param searchDailyParams
     * @return
     */
    public boolean createChangeData(SearchDailyParams searchDailyParams) {

        // 변경작성 대상의 범위 설정(해당일자 부터 마지막 승인된 데이터의 일보까지).
        List<DdBrfg> changes = masterMapper.changeAvailiableMasterList(searchDailyParams);

        // 일보 변경 번호 채번
        DdBrfgChgMtr chgMtr = new DdBrfgChgMtr();
        chgMtr.setChgNo(changeMapper.maxChgNo());

        int i = 0;
        for (DdBrfg change : changes) {

            // 변경대상 일자만 변경작성이고, 나머지는 홀드 상태이다.
            String sta_cd = change.getDay().equals(searchDailyParams.getDay()) ? DdBrfgStaCd.CHANGE.getValue() : DdBrfgStaCd.HOLD.getValue();
            String chg_rsn = change.getDay().equals(searchDailyParams.getDay()) ? DdBrfgStaCd.CHANGE.getName() : DdBrfgStaCd.HOLD.getName();

            // 일보 변경 생성
            int seq = ++i;
            chgMtr.setChgSeq(seq);
            chgMtr.setStaCd(sta_cd);
            changeMapper.insert(chgMtr);

            // 일보 생성
            DdBrfg master = new DdBrfg();
            master.setDeptCd(change.getDeptCd());
            master.setDay(change.getDay());
            master.setWeatherCd(change.getWeatherCd());
            master.setHighestTempe(change.getHighestTempe());
            master.setLowestTempe(change.getLowestTempe());
            master.setPtclMtr(change.getPtclMtr());
            master.setChgNo(chgMtr.getChgNo());
            master.setChgSeq(seq);
            master.setStaCd(sta_cd);
            masterMapper.insert(master);

            // 일보 이력 생성
            dailyCommonService.createStaHistory(master, chg_rsn);

            // 해당 일보에 속한 일보 회사 목록 만큼 신규 차수 데이터를 생성
            for (DdBrfgComp changeComp : compMapper.getListInChange(change)) {
                // 일보 회사 생성
                DdBrfgComp comp = changeComp.copy();
                comp.setOrdrNo(master.getOrdrNo());
                compMapper.insert(comp);
            }

            // 일보 회사 이력 생성
            for (DdBrfgCompStaHst changeCompStaHst : compStaHstMapper.getListInChange(change)) {
                // 일보 회사 생성
                DdBrfgCompStaHst compStaHst = changeCompStaHst.copy();
                compStaHst.setOrdrNo(master.getOrdrNo());
                compStaHstMapper.insert(compStaHst);
            }

            // 일보 출역 생성
            for (DdBrfgLabatd changeLabatd : labatdMapper.getListInChange(change)) {
                DdBrfgLabatd labatd = changeLabatd.copy();
                labatd.setOrdrNo(master.getOrdrNo());
                labatdMapper.insert(labatd);
            }

            // 일보 자재 생성
            for (DdBrfgMtil changeMtil : mtilMapper.getListInChange(change)) {
                DdBrfgMtil mtil = changeMtil.copy();
                mtil.setOrdrNo(master.getOrdrNo());
                mtilMapper.insert(mtil);
            }

            // 일보 장비 생성
            for (DdBrfgEqip changeEqip : eqipMapper.getListInChange(change)) {
                DdBrfgEqip eqip = changeEqip.copy();
                eqip.setOrdrNo(master.getOrdrNo());
                eqipMapper.insert(eqip);
            }

            // 일보 물량 생성
            for (DdBrfgQty changeQty : qtyMapper.getListInChange(change)) {
                DdBrfgQty qty = changeQty.copy();
                qty.setOrdrNo(master.getOrdrNo());
                qtyMapper.insert(qty);
            }
        }

        return true;
    }

    /**
     * 공사일보 변경작성 데이터 삭제.
     *
     * @param searchDailyParams
     * @return
     */
    public boolean cancelChangeData(SearchDailyParams searchDailyParams) {

        // 변경작성 된 데이터 조회
        List<DdBrfg> changedList = masterMapper.changedMasterList(searchDailyParams);

        for (DdBrfg changed : changedList) {

            // 일보 물량 삭제
            qtyMapper.deleteChanged(changed);

            // 일보 장비 삭제
            eqipMapper.deleteChanged(changed);

            // 일보 자재 삭제
            mtilMapper.deleteChanged(changed);

            // 일보 출역 삭제
            labatdMapper.deleteChanged(changed);

            // 일보 회사 이력 삭제
            compStaHstMapper.deleteChanged(changed);

            // 일보 회사 삭제
            compMapper.deleteChanged(changed);

            // 일보 이력 삭제
            staHstMapper.deleteChanged(changed);
        }

        // 일보 결재 이력 삭제
        aprvMapper.deleteChanged(searchDailyParams);

        // 일보 삭제
        masterMapper.deleteChanged(searchDailyParams);

        // 일보 변경 삭제
        changeMapper.deleteChanged(searchDailyParams);

        return true;
    }

    /**
     * 공사일보 협력사 상태 변경이력 조회.
     *
     * @param searchDailyParams
     * @return
     */
    public List<DdBrfgCompStaHst> getChangedCompStaHistories(SearchDailyParams searchDailyParams) {
        return compStaHstMapper.getChangedHistories(searchDailyParams);
    }
}
