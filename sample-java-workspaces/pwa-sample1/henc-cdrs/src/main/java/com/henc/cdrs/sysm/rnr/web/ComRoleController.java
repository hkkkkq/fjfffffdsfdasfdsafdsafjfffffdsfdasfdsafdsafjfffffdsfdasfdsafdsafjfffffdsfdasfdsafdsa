package com.henc.cdrs.sysm.rnr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.henc.cdrs.sysm.sec.auth.security.common.SecurityUtil;
import com.henc.cdrs.sysm.sec.auth.security.config.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.common.code.model.Code;
import com.henc.cdrs.common.code.model.ComMenuPgmBtn;
import com.henc.cdrs.common.code.model.ComNmspcInfo;
import com.henc.cdrs.common.code.service.CodeService;
import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.sysm.rnr.model.ComRole;
import com.henc.cdrs.sysm.rnr.model.ComRoleDept;
import com.henc.cdrs.sysm.rnr.model.ComRoleMenu;
import com.henc.cdrs.sysm.rnr.service.ComRoleService;

@Controller
@RequestMapping("/sysm/sec/rnr")
public class ComRoleController extends BaseController{

    @Autowired
    ComRoleService comRoleService;
    
    @Autowired
    CodeService codeService;    
    
    @Autowired
    HttpServletRequest request;     
    
    @GetMapping("/roleList")
    public String roleList(Model model) {
        String tokenPayload = SecurityUtil.getCookie(request, SecurityConstants.CDRS_REFRESH_TOKEN);

        model.addAttribute("accessToken", tokenPayload);

        return "views/sysm/sec/rnr/roleList";        
    }
    
    /**
     * 개별 권한 모달
     * @param roleId
     */     
    @GetMapping({"/dataRoleModal/{roleId}"})
    public String dataRoleModal(@PathVariable String roleId, Model model) {
        model.addAttribute(roleId);
        return  "views/sysm/sec/rnr/dataRoleModal";     
    }
    
    /**
     * 부분 권한 모달
     * @param roleId
     */     
    @GetMapping({"/dataRolePartialModal/{roleId}"})
    public String dataRolePartialModal(@PathVariable String roleId, Model model) {
        model.addAttribute(roleId);
        return  "views/sysm/sec/rnr/dataRolePartialModal";     
    }    
    
    /**
     * 메뉴 추가 모달
     */     
    @GetMapping("/rootMenuModal")
    public String rootMenuModal() {
        return  "views/sysm/sec/rnr/rootMenuModal";     
    }      
    
    /**
     * 메뉴 그룹 추가 모달
     */     
    @GetMapping("/menuGrpModal")
    public String menuGrpModal() {
        return  "views/sysm/sec/rnr/menuGrpModal";     
    }       
    
    /**
     * 프로그램 추가 모달
     */     
    @GetMapping("/pgmModal")
    public String pgmModal() {
        return  "views/sysm/sec/rnr/pgmModal";     
    }       
    
    /**
     * 담당 코드(ORG_DEPT_LEV2) 조회
     * @param 
     */     
    @GetMapping("/dataRolePartialModal/getOrgDeptLev2")
    public @ResponseBody  List<Map<String, ModelMap>> getOrgDeptLev2() {
        
        List<CamelCaseMap> orgDeptLev1 = codeService.getCodeList("ORG_DEPT_LEV1");
        List<Map<String, ModelMap>> addlist = new ArrayList<Map<String, ModelMap>>();
        for (CamelCaseMap list : orgDeptLev1 ) {
            
            Code codeMap = new Code();
            codeMap.setStdCd("ORG_DEPT_LEV2");
            codeMap.setUserDefiCd(list.get("key").toString());            
            
            List<CamelCaseMap> orgDeptLev2 = codeService.getCodeList(codeMap);
            
            Map<String, ModelMap> addMap = new HashMap<String, ModelMap>();
            addMap.put(list.get("key").toString(), codeService.makeIBSheetCombo(list.get("key").toString(), orgDeptLev2, true, "", "전체"));
            addlist.add(addMap);
        }

        return addlist;          
    }    
    
    
    @GetMapping(value = "/roleList/selectComRoleList")
    public @ResponseBody List<CamelCaseMap> getComRoleList(ComRole comRole){
        return comRoleService.selectComRoleList(comRole); 
    }

    /**
     * 메뉴에 연결되어 있는 프로그램 조회
     * @param ComRole
     * @return
     */    
    @GetMapping(value = "/roleList/getRoleMenuPgm")
    public @ResponseBody CamelCaseMap getRoleMenuPgm(ComRole role){
        CamelCaseMap comrole = comRoleService.getComRoleMenuPgm(role);
        return comrole == null ? new CamelCaseMap() : comrole;
    }    
    
    /**
     * 버튼 시트 조회
     * @param ComRole
     * @returnComRole
     */    
    @GetMapping(value = "/roleList/btnGridList")
    public @ResponseBody List<CamelCaseMap> btnGridList(ComRole role){    
        return comRoleService.getComMenuPgmBtnList(role);
    }    
        
    @PostMapping(value = "/roleList/saveComRoleList")
    public @ResponseBody ComRole saveEmp(ComRole comRole){    
        comRoleService.saveComRoleList(comRole.getComRoles(), getUserContext(request).getUserId());
        return comRole; 
    }
    
    @GetMapping(value = "/roleList/getRoleMenuList")
    public @ResponseBody String getRoleMenuList(ComRole comRole){    
        return comRoleService.getComRoleMenuList(comRole);
    }
    
    @PostMapping(value = "/roleList/createMenu")
    public @ResponseBody ComRoleMenu createMenu(ComRoleMenu comRoleMenu){
        this.comRoleService.createComRoleMenu(comRoleMenu);
        return comRoleMenu;
    }
    
    @PostMapping(value = "/roleList/deleteMenu")
    public @ResponseBody ComRoleMenu deleteMenu(ComRoleMenu comRoleMenu){
        this.comRoleService.deleteComRoleMenu(comRoleMenu);
        return comRoleMenu;
    }
    
    /**
     * 메뉴 그룹 모달 창 조회
     * @param ComNmspcInfo
     * @return
     */         
    @GetMapping(value = "/roleList/menuGrpList") 
    public @ResponseBody List<CamelCaseMap> menuGrpList(ComNmspcInfo comNmspcInfo){
        return comRoleService.getNameSpaceList(comNmspcInfo);
    }
    
    /**
     * 메뉴 그룹 모달 저장
     * @param ComNmspcInfo
     * @return
     */        
    @PostMapping(value = "/roleList/saveNameSpaceList")    
    public @ResponseBody ComNmspcInfo saveNameSpaceList(ComNmspcInfo comNmspcInfo){
        comRoleService.saveNameSpaceList(comNmspcInfo.getComNmspcInfos());        
        return comNmspcInfo;             
    }    
    
    /**
     * 프로그램 관리 조회
     * @param map
     * @return
     */    
    @GetMapping(value = "/roleList/pgmGridList") 
    public @ResponseBody List<CamelCaseMap> pgmGridList(@RequestParam Map<String,String> map){
        return comRoleService.getComPgmList(map);
    }    
    
    /**
     * 권한 메뉴 저장 
     * @param map
     * @return
     */
    @PutMapping(value = "/roleList/modifyRoleMenu")
    public @ResponseBody ComRoleMenu modifyRoleMenu(@Valid ComRoleMenu comRoleMenu){
        
        if ("CHANGE_PGM".equals(comRoleMenu.getPgmSaveMode())) {
            ComMenuPgmBtn comMenuPgmBtn = new ComMenuPgmBtn();
            comMenuPgmBtn.setRoleId(comRoleMenu.getRoleId());
            comMenuPgmBtn.setMenuId(comRoleMenu.getMenuId());
            comMenuPgmBtn.setPgmId(comRoleMenu.getPgmId());
            comRoleService.deleteAllComMenuPgmBtn(comMenuPgmBtn);
        }        
                   
        comRoleService.modifyComRoleMenu(comRoleMenu);
        return comRoleMenu;
    }
    
    /**
     * 버튼 그리드 내역 저장
     * @param modList
     * @return
     */
    @PostMapping(value = "/roleList/btnGridSave")
    public @ResponseBody ComMenuPgmBtn btnGridSave(@Valid ComMenuPgmBtn comMenuPgmBtn){
        comRoleService.saveComMenuPgmBtnList(comMenuPgmBtn.getList());      
        return comMenuPgmBtn;    
    }    
    
    @GetMapping("/roleListPopup")
    public String roleListPopup(Model model) {
        return "views/sysm/sec/rnr/roleListPopup";
    }

    @GetMapping("/roleListPopup/roleListPopupList")
    public @ResponseBody List<CamelCaseMap> roleListPopupList(ComRole comRole) {     
        return comRoleService.selectComRoleList(comRole); 
    }
    
    /**
     * 메뉴 이동
     * @param HttpServletRequest
     * @return ModelAndView
     */
    @GetMapping(value = "/roleList/menuMove" )     
    public @ResponseBody List<CamelCaseMap> menuMove(ComRoleMenu comRoleMenu){
        return comRoleService.moveComRoleMenu(comRoleMenu);
    }    
    
    /**
     * 접근 가능한 부서코드 조회
     * @param searchParameters
     * @return
     */
    @GetMapping(value = "/roleList/gridComRoleDeptList")    
    public @ResponseBody List<CamelCaseMap> gridComRoleDeptList(ComRoleDept comRoleDept){
        return comRoleService.getComRoleDeptList(comRoleDept);
    }    
        
    /**
     * 데이터 권한 IBSHEET 저장
     * @param map
     * @return
     */  
    @PostMapping(value = "/roleList/gridComRoleDeptSave") 
    public @ResponseBody ComRoleDept gridComRoleDeptSave(ComRoleDept comRoleDept){
        comRoleService.saveComRoleDeptList(comRoleDept.getComRoleDepts(), getUserContext(request).getUserId());
        return comRoleDept;             
    }       
    
    /**
     * 부서코드 리스트 조회
     * @param 
     * @return
     */
    @GetMapping(value = "/roleList/gridComDeptCdList")
    public @ResponseBody List<CamelCaseMap> gridComDeptCdList(ComRoleDept comRoleDept){
//        comRoleDept.setDeptCd(getUserContext(request).getLastDeptCd());
        return comRoleService.getComDeptCdList(comRoleDept);
    }   
    
}
