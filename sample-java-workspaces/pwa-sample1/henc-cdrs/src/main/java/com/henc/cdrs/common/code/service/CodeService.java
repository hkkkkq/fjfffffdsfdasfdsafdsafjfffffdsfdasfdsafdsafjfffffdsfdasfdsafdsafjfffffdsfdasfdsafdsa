package com.henc.cdrs.common.code.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.common.code.model.Code;
import com.henc.cdrs.common.code.repository.CodeMapper;

@Service
@Transactional
public class CodeService {

    @Autowired
    private CodeMapper mapper;

    public List<CamelCaseMap> getCodeList(String stdCd) {
        return this.getCodeList(stdCd, null);
    }

    public List<CamelCaseMap> getCodeList(String stdCd, String stdDetlCd) {
        return this.getCodeList(stdCd, stdDetlCd, null, null);
    }

    public List<CamelCaseMap> getCodeList(String stdCd, String stdDetlCd, Integer attrId, String attrVal) {
        return this.getCodeList(stdCd, stdDetlCd, attrId, attrVal, null, false);
    }

    /**
     * 원하는 코드값을 ATTRIBUTE_ID 와 ATTRIBUTE_VAL 조건을 추가하여 조회한다<br>
     * ATTRIBUTE_ID 와 ATTRIBUTE_VAL 은 NULL이여도 된다.
     *
     * @param stdCd            기준코드
     * @param stdDetlCd        기준상세코드
     * @param attrId           COM_ATTRIBUTE_VAL 테이블의 attribute_id
     * @param attrVal          COM_ATTRIBUTE_VAL 테이블의 attribute_val
     * @param inOrExList       기준상세코드를 in 절로 담기 위한 리스트
     * @param includeOrExclude inOrExList 을 사용할때
     * @return 코드 키, 코드 값 이 담긴 Map 의 List
     */

    public List<CamelCaseMap> getCodeList(String stdCd, String stdDetlCd, Integer attrId, String attrVal, List<String> inOrExList, boolean includeOrExclude) {

        List<CamelCaseMap> codeList = new ArrayList<>();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("stdCd", stdCd);

        if (stdDetlCd != null) {
            paramMap.put("stdDetlCd", stdDetlCd);
        }
        if (attrId != null) {
            paramMap.put("attrId", attrId);
        }
        if (attrVal != null) {
            paramMap.put("attrVal", attrVal);
        }

        if (includeOrExclude) {
            paramMap.put("includeCodeList", inOrExList);
        } else {
            paramMap.put("excludeCodeList", inOrExList);
        }

        List<CamelCaseMap> rs = mapper.selectCodeList(paramMap);

        for (CamelCaseMap map : rs) {
            CamelCaseMap codeMap = new CamelCaseMap();
            codeMap.put("key", map.get("stdDetlCd"));
            codeMap.put("value", map.get("nmspcVal"));
            codeList.add(codeMap);
        }

        return codeList;
    }

    public List<CamelCaseMap> getCodeList(Code code) {
        List<CamelCaseMap> codeList = new ArrayList<>();

        List<CamelCaseMap> rs = mapper.selectCodeList(code);

        for (CamelCaseMap List : rs) {
            CamelCaseMap codeMap = new CamelCaseMap();
            codeMap.put("key", List.get("stdDetlCd"));
            codeMap.put("value", List.get("nmspcVal"));
            codeList.add(codeMap);
        }

        return codeList;
    }

    public ModelMap makeIBSheetCombo(String stdCd) {
        return this.makeIBSheetCombo(stdCd, this.getCodeList(stdCd), false, null, null);
    }

    public ModelMap makeIBSheetCombo(String stdCd, List<CamelCaseMap> list) {
        return this.makeIBSheetCombo(stdCd, list, false, null, null);
    }

    public ModelMap makeIBSheetCombo(String stdCd, List<CamelCaseMap> list, boolean isDefault, String defaultCode, String defaultValue) {
        String ibsheetComboCode = (isDefault ? defaultCode + "|" : "");
        String ibsheetComboValue = (isDefault ? defaultValue + "|" : "");
        ModelMap map = new ModelMap();

        for (Map<String, Object> combo : list) {
            ibsheetComboCode += combo.get("key") + "|";
            ibsheetComboValue += combo.get("value") + "|";
        }
        if (!"".equals(ibsheetComboCode)) {
            ibsheetComboCode = ibsheetComboCode.substring(0, ibsheetComboCode.length() - 1);
        }
        if (!"".equals(ibsheetComboValue)) {
            ibsheetComboValue = ibsheetComboValue.substring(0, ibsheetComboValue.length() - 1);
        }

        map.put("IBCC_" + stdCd, ibsheetComboCode);
        map.put("IBCV_" + stdCd, ibsheetComboValue);

        return map;
    }

    public List<CamelCaseMap> selectCodeAttributeList(Code code) {
        return mapper.selectCodeAttributeList(code);
    }

}
