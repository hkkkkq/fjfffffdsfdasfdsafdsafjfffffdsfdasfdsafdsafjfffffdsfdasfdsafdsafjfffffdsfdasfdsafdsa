package com.henc.cdrs.sysm.rnr.service;

import com.henc.cdrs.common.code.model.ComMenuPgmBtn;
import com.henc.cdrs.common.code.model.ComNmspcInfo;
import com.henc.cdrs.sysm.rnr.model.ComRole;
import com.henc.cdrs.sysm.rnr.model.ComRoleDept;
import com.henc.cdrs.sysm.rnr.model.ComRoleMenu;
import com.henc.cdrs.sysm.rnr.repository.ComRoleMapper;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ComRoleService {

    @Autowired
    ComRoleMapper comRoleMapper;

    @Autowired
    HttpServletRequest request;

    public List<CamelCaseMap> selectComRoleList(ComRole comRole) {
        return comRoleMapper.selectComRoleList(comRole);
    }

    public CamelCaseMap getComRoleMenuPgm(ComRole role) {
        return comRoleMapper.getComRoleMenuPgm(role);
    }

    public List<CamelCaseMap> getComMenuPgmBtnList(ComRole role) {
        return comRoleMapper.selectComMenuPgmBtnList(role);
    }

    public void saveComRoleList(List<ComRole> comRoles, String userId) {
        if (CollectionUtils.isNotEmpty(comRoles)) {
            for (ComRole comRole : comRoles) {
                switch (comRole.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        comRoleMapper.insertComRole(comRole);
                        comRoleMapper.mergeRoleNamespace(comRole);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        comRoleMapper.updateComRole(comRole);
                        comRoleMapper.mergeRoleNamespace(comRole);
                        break;
                    case IBSheetRowStatus.DELETED:
                        comRoleMapper.deleteComRole(comRole);
                        comRoleMapper.deleteRoleNamespace(comRole);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(comRole.getRowStatus());
                }
            }
        }
    }

    public String getComRoleMenuList(ComRole comRole) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("p_role_id", comRole.getRoleId());
        comRoleMapper.getRoleMenuList(map);
        return map.get("p_xml_data").toString();
    }

    public void createComRoleMenu(ComRoleMenu comRoleMenu) {
        comRoleMapper.createComRoleMenu(comRoleMenu);
    }

    public void deleteComRoleMenu(ComRoleMenu comRoleMenu) {
        comRoleMapper.deleteComRoleMenu(comRoleMenu);
    }

    public List<CamelCaseMap> getNameSpaceList(ComNmspcInfo comNmspcInfo) {
        return comRoleMapper.selectNameSpaceList(comNmspcInfo);
    }

    public void saveNameSpaceList(List<ComNmspcInfo> comNmspcInfos) {
        if (CollectionUtils.isNotEmpty(comNmspcInfos)) {
            for (ComNmspcInfo comNmspcInfo : comNmspcInfos) {
                switch (comNmspcInfo.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        comRoleMapper.insertComNmspcInfo(comNmspcInfo);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        comRoleMapper.updateComNmspcInfo(comNmspcInfo);
                        break;
                    case IBSheetRowStatus.DELETED:
                        comRoleMapper.deleteComNmspcInfo(comNmspcInfo);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(comNmspcInfo.getRowStatus());
                }
            }
        }
    }

    public List<CamelCaseMap> getComPgmList(Map<String, String> map) {
        return comRoleMapper.selectComPgmList(map);
    }

    public void deleteAllComMenuPgmBtn(ComMenuPgmBtn comMenuPgmBtn) {
        this.comRoleMapper.deleteAllComMenuPgmBtn(comMenuPgmBtn);
    }

    public void modifyComRoleMenu(ComRoleMenu comRoleMenu) {
        this.comRoleMapper.updateComRoleMenu(comRoleMenu);
    }

    public void saveComMenuPgmBtnList(List<ComMenuPgmBtn> updList) {
        if (updList.size() > 0) {
            for (ComMenuPgmBtn comMenuPgmBtn : updList) {
                if ("Y".equals(comMenuPgmBtn.getBtnUseYn())) {
                    this.comRoleMapper.deleteComMenuPgmBtn(comMenuPgmBtn);
                } else {
                    this.comRoleMapper.insertComMenuPgmBtn(comMenuPgmBtn);
                }
            }
        }
    }

    public List<CamelCaseMap> moveComRoleMenu(ComRoleMenu comRoleMenu) {
        return this.comRoleMapper.moveComRoleMenu(comRoleMenu);
    }

    public List<CamelCaseMap> getComRoleDeptList(ComRoleDept comRoleDept) {
        return comRoleMapper.selectComRoleDeptList(comRoleDept);
    }

    public void saveComRoleDeptList(List<ComRoleDept> comRoleDepts, String userId) {
        if (CollectionUtils.isNotEmpty(comRoleDepts)) {
            for (ComRoleDept comRoleDept : comRoleDepts) {
                switch (comRoleDept.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        comRoleMapper.insertComRoleDeptList(comRoleDept);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        comRoleMapper.updateComRoleDeptList(comRoleDept);
                        break;
                    case IBSheetRowStatus.DELETED:
                        comRoleMapper.deleteComRoleDeptList(comRoleDept);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(comRoleDept.getRowStatus());
                }
            }
        }
    }

    public List<CamelCaseMap> getComDeptCdList(ComRoleDept comRoleDept) {
        return comRoleMapper.selectComDeptCdList(comRoleDept);
    }

}
