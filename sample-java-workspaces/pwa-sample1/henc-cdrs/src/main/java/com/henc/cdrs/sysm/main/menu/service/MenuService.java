package com.henc.cdrs.sysm.main.menu.service;

import com.henc.cdrs.sysm.main.menu.model.Menu;
import com.henc.cdrs.sysm.main.menu.repository.MenuMapper;
import com.henc.dream.util.CamelCaseMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MenuService {
    @Autowired
    private MenuMapper mapper;

    public String selectMenuTag(String roleId) {

        return selectMenuTag(roleId, null);
    }

    public String selectMenuTag(String roleId, String uri) {
        List<Menu> menuList = mapper.selectMenuList(roleId);
        CamelCaseMap activeMenu = new CamelCaseMap();

        if(uri != null){
            activeMenu = mapper.selectActiveMenuId(roleId, uri);
        }

        String menuTag = "";
        int i = 0;
        String parentMenuId = "";
        for (Menu menu : menuList) {

            if (menu.getLvl() == 1) {
                parentMenuId = menu.getMenuId();
                String parentCss = "";
                String naviClss = "";
                if(activeMenu != null && activeMenu.get("activeParentMenuId").equals(parentMenuId)){
                    parentCss = "show";
                    naviClss = "active";
                }

//                menuTag += "<hr class=\"sidebar-divider\">\n";
                if(!"DWPR".equals(menu.getSysClsCd())){
                    naviClss += " d-tab";
                }else{

                }


                menuTag += "<li class=\"nav-item "+naviClss+"\">\n";

                if (menu.getNxtUprMenuId().equals(menu.getMenuId())) {

                    menuTag += "    <a class=\"nav-link\" href=\"#none\" data-toggle=\"collapse\" data-target=\"#"+parentMenuId+"\" aria-expanded=\"true\" aria-controls=\""+parentMenuId+"\"><span>"+menu.getNmspcVal()+"</span></a>\n";

                    menuTag += "<div id=\""+parentMenuId+"\" class=\"collapse "+parentCss+"\" aria-labelledby=\"headingTwo\" data-parent=\"#menuUl\">\n";
                    menuTag += "<div class=\"collapse-inner\">\n";

                } else {

                    menuTag += "    <a class=\"nav-link\" href=\""+menu.getUriNm()+"\"><span>"+menu.getNmspcVal()+"</span></a>\n";
                    menuTag += "</li>\n";
                }

            } else if (menu.getLvl() == 2) {
                if (menu.getUprMenuId().equals(parentMenuId)) {
                    String activeCss = "";
                    if(activeMenu != null && activeMenu.get("activeMenuId").equals(menu.getMenuId())) {
                        activeCss = "active";
                    }
                    menuTag += "<a class=\"collapse-item "+activeCss+"\" href=\""+menu.getUriNm()+"\">"+menu.getNmspcVal()+"</a>\n";
                }

                if (!menu.getNxtUprMenuId().equals(parentMenuId)) {
                    menuTag += "</div>\n";
                    menuTag += "</div>\n";
                    menuTag += "</li>\n";

                }
            }
            i++;
        }
//        menuTag += "<hr class=\"sidebar-divider\">\n";
        return menuTag;
    }

    /**
     * @param map
     * @return
     * @deprecated
     */
    public List<CamelCaseMap> selectMenuTagList(Map<String, String> map) {
        return mapper.selectMenuTagList(map);
    }

}

