package com.henc.cdrs.sysm.sec.usr.service;

import com.henc.cdrs.sysm.rnr.model.ComUserRole;
import com.henc.cdrs.sysm.sec.usr.model.ComUser;
import com.henc.cdrs.sysm.sec.usr.repository.ComUserRoleMapper;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ComUserRoleService {

    @Autowired
    ComUserRoleMapper comUserRoleMapper;

    @Autowired
    HttpServletRequest request;


    public List<CamelCaseMap> gridUserRoleList(ComUser comUser) {
        return comUserRoleMapper.gridUserRoleList(comUser);
    }

    public ComUser saveComUser(ComUser comUser) {
        saveComUserRoleList(comUser.getComUserRoles(), comUser.getUserId());
        return comUser;
    }

    public void saveComUserRoleList(List<ComUserRole> comUserRoles, String userId) {
        if (CollectionUtils.isNotEmpty(comUserRoles)) {
            for (ComUserRole comUserRole : comUserRoles) {
                comUserRole.setUserId(userId);
                switch (comUserRole.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        comUserRoleMapper.insertComUserRole(comUserRole);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        comUserRoleMapper.updateComUserRole(comUserRole);
                        break;
                    case IBSheetRowStatus.DELETED:
                        comUserRoleMapper.deleteComUserRole(comUserRole);
                        break;
                    default:
                        throw new UnsupportedRowTypeException(comUserRole.getRowStatus());
                }
            }
        }
    }


    public String getUserMenuXmlData(String userId, String roleId) {
        String xmlData = "";
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("p_role_id", roleId);
        map.put("p_user_id", userId);
        comUserRoleMapper.getUserMenuXmlData(map);
        xmlData = map.get("p_xml_data").toString();
        return xmlData;
    }

    public boolean hasAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent();
    }

}
