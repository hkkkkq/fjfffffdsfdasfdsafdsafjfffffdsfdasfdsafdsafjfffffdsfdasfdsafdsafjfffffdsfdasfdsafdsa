package com.kt.kkos.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ITLMaskRule {
    private HashMap<String, Object> fildMaskMap;
    private HashMap<String, Object> swtcMaskMap;
    private HashMap<String, Object> paramMaskMap;
    private HashMap<String, Object> tcpMaskMap;
    
    public ITLMaskRule()
    {
        fildMaskMap = new HashMap<String, Object>();
        swtcMaskMap = new HashMap<String, Object>();
        paramMaskMap = new HashMap<String, Object>();
        tcpMaskMap = new HashMap<String, Object>();
    }

    public HashMap<String, Object> getFildMaskMap() {
        return fildMaskMap;
    }

    public HashMap<String, Object> getSwtcMaskMap() {
        return swtcMaskMap;
    }

    public HashMap<String, Object> getParamMaskMap() {
        return paramMaskMap;
    }
    
    public HashMap<String, Object> getTcpMaskMap() {
        return tcpMaskMap;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public HashMap<String, Object> getTcpMaskMap(String srvrId, String aplyWrkjob, String maskTypeCd)
    {
        
        HashMap<String, Object> map = new HashMap<String, Object>();

        for(Map.Entry entry: tcpMaskMap.entrySet())
        {
            HashMap<String, Object> tcpRule = (HashMap<String, Object>)entry.getValue();
            String key = (String)entry.getKey();
            
            if(maskTypeCd.equals(ITLKeyUTIL.TCP_GEAR_SBST_MASK_BY_KEY))
            {
                if(key.substring(0, srvrId.length() + 2).equals(srvrId + "::"))
                {
                    map.put(key.substring(srvrId.length()+2), tcpRule.get("ENCD_FILD_TYPE_CD") + "::" + tcpRule.get("CD_DTL_DSCR"));
                }
            }
            else if(maskTypeCd.equals(ITLKeyUTIL.TCP_GEAR_SBST_MASK_BY_COORD))
            {
                if(key.substring(0, srvrId.length() + 2 + aplyWrkjob.length() + 2).equals(srvrId + "::" + aplyWrkjob + "::"))
                {
                    map.put("RULE", tcpRule.get("ENCD_FILD_TYPE_CD"));
                }
            }
        }
        
        return map;
    }
}
