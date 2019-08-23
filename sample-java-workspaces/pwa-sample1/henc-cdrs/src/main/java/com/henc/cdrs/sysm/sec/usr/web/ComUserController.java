package com.henc.cdrs.sysm.sec.usr.web;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.sysm.rnr.model.ComRole;
import com.henc.cdrs.sysm.rnr.model.SearchComRoleDept;
import com.henc.cdrs.sysm.rnr.service.ComRoleDeptService;
import com.henc.cdrs.sysm.rnr.service.ComRoleService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.auth.model.UserSign;
import com.henc.cdrs.sysm.sec.auth.security.common.SecurityUtil;
import com.henc.cdrs.sysm.sec.usr.model.*;
import com.henc.cdrs.sysm.sec.usr.service.*;
import com.henc.dream.util.CamelCaseMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/sysm/sec/usr")
public class ComUserController extends BaseController {

    @Autowired
    ComUserService comUserService;

    @Autowired
    ComRoleService comRoleService;

    @Autowired
    ComUserRoleService comUserRoleService;

    @Autowired
    ComRoleDeptService comRoleDeptService;

    @Autowired
    ComUserRoleDeptService comUserRoleDeptService;

    @Autowired
    ComUserChgMenuBtnService comUserChgMenuBtnService;

    @Autowired
    ComUserChgMenuService comUserChgMenuService;

    @Autowired
    HttpServletRequest request;

    /**
     * 사용자 관리
     *
     * @return
     */
    @GetMapping("/userList")
    public String userList(Model model) {
        comUserRoleService.hasAdminRole();
        return "views/sysm/sec/usr/userList";
    }

    /**
     * 사용자 관리 - 조회
     *
     * @param comUserSearch
     * @return
     */
    @GetMapping(value = "/gridList")
    public @ResponseBody
    List<CamelCaseMap> gridList(ComUserSearch comUserSearch) {
        return comUserService.getUserList(comUserSearch);
    }

    /**
     * 사용자 계정 정보 상세
     *
     * @param userId
     * @return
     */
    @GetMapping({"/userEdit", "/userEdit/{userId}"})
    public String userEdit(@PathVariable Optional<String> userId, Model model) {
        model.addAttribute("userId", userId.orElse(null));
        model.addAttribute("roles", comRoleService.selectComRoleList(new ComRole()));

        return "views/sysm/sec/usr/userEdit";
    }

    /**
     * 사용자 계정 정보 상세
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/userEdit/getComUser")
    public @ResponseBody
    ComUser getComUser(String userId) {
        return comUserService.getComUser(userId);
    }

    /**
     * 사용자 계정 정보 - 권한 조회
     *
     * @param comUser
     * @return
     */
    @GetMapping("/userEdit/gridUserRoleList")
    public @ResponseBody
    List<CamelCaseMap> gridUserRoleList(ComUser comUser) {
        return comUserRoleService.gridUserRoleList(comUser);
    }

    /**
     * 사용자 계정 정보 - 저장
     *
     * @param comUser
     * @return
     */
    @PostMapping(value = "/userSave")
    public @ResponseBody
    ComUser userSave(ComUser comUser) {
        comUserService.updateComUser(comUser);
        comUserRoleService.saveComUserRoleList(comUser.getComUserRoles(), comUser.getUserId());
        comUserService.saveUserDeptList(comUser.getUserDepts(), comUser.getUserId());

        return comUser;
    }

    /**
     * 사용자 계정 정보 - 신규저장
     *
     * @param comUser
     * @return
     */
    @PostMapping(value = "/userInsert")
    public @ResponseBody ComUser userInsert(ComUser comUser) {
        comUserService.insertComUser(comUser);
        comUserRoleService.saveComUserRoleList(comUser.getComUserRoles(), comUser.getUserId());
        comUserService.saveUserDeptList(comUser.getUserDepts(), comUser.getUserId());

        return comUser;
    }

    /**
     * 비밀번호 변경
     *
     * @param comUser
     * @param request
     * @return
     */
    @PostMapping(value = "/updatePwd")
    public @ResponseBody
    ComUser updatePwd(ComUser comUser, HttpServletRequest request) {
        comUserService.updatePwd(comUser);

        return comUser;
    }

    /**
     * 데이터 조회 권한 - 조회
     *
     * @param searchComRoleDept
     * @return
     */
    @GetMapping(value = "/userDataRolePopupList")
    public @ResponseBody
    SearchComRoleDept userDataRolePopupList(SearchComRoleDept searchComRoleDept) {

        //사용자 권한 부서 코드 조회
        if ("A".equals(searchComRoleDept.getDatRangCd())) {//데이터 조회 범위 (전체)
            searchComRoleDept.setComRoleDepts(comRoleDeptService.getComRoleDeptAllList(searchComRoleDept));
        } else if ("P".equals(searchComRoleDept.getDatRangCd())) {//데이터 조회 범위 (부분)
            searchComRoleDept.setComRoleDepts(comRoleDeptService.getComRoleDeptPartialList(searchComRoleDept));
        } else if ("I".equals(searchComRoleDept.getDatRangCd())) {//데이터 조회 범위 (개별)
            searchComRoleDept.setComRoleDepts(comRoleDeptService.getComRoleDeptIndivisualList(searchComRoleDept));
        }

        //사용자 권한 접근 가능한 예외부서 코드 조회
        searchComRoleDept.setUseYn("Y");
        searchComRoleDept.setComUserRoleDeptsYes(comUserRoleDeptService.getComUserRoleDeptList(searchComRoleDept));

        //사용자 권한 접근 불가능한 예외부서 코드 조회
        searchComRoleDept.setUseYn("N");
        searchComRoleDept.setComUserRoleDeptsNo(comUserRoleDeptService.getComUserRoleDeptList(searchComRoleDept));

        return searchComRoleDept;
    }

    /**
     * 데이터 조회 권한 - 저장
     *
     * @param searchComRoleDept
     * @return
     */
    @PostMapping(value = "/gridUserRoleDeptSave")
    public @ResponseBody
    SearchComRoleDept gridUserRoleDeptSave(SearchComRoleDept searchComRoleDept) {
        comUserRoleDeptService.gridUserRoleDeptSave(searchComRoleDept.getComUserRoleDeptsYes(), searchComRoleDept.getComUserRoleDeptsNo(), getUserContext(request).getUserId());
        return searchComRoleDept;
    }

    /**
     * Role define - 사용자 권한 메뉴 Tree XML 데이터 조회
     *
     * @param userRoleMenuTreeSearch
     * @return
     */
    @GetMapping(value = "/getUserRoleMenuTreeData")
    public @ResponseBody
    String getUserRoleMenuTreeData(UserRoleMenuTreeSearch userRoleMenuTreeSearch) {
        return comUserRoleService.getUserMenuXmlData(userRoleMenuTreeSearch.getUserId(), userRoleMenuTreeSearch.getRoleId());
    }

    /**
     * Role define - 사용자 권한 개별 버튼 정의 목록 조회
     *
     * @param comUserChgMenuBtn
     * @return
     */
    @GetMapping(value = "/comUserChgMenuBtnGridList")
    public @ResponseBody
    List<CamelCaseMap> comUserChgMenuBtnGridList(ComUserChgMenuBtn comUserChgMenuBtn) {
        return comUserChgMenuBtnService.getComUserChgMenuBtnList(comUserChgMenuBtn);
    }

    /**
     * Role define - 저장
     *
     * @param comUserChgMenu
     * @return
     */
    @PostMapping(value = "/comUserChgMenuSave")
    public @ResponseBody
    ComUserChgMenu comUserChgMenuSave(ComUserChgMenu comUserChgMenu) {

        //FROM 저장
        if ("N".equals(comUserChgMenu.getUserDefineUseYn())) {
            //사용자 정의 메뉴 delete
            comUserChgMenuService.deleteComUserChgMenu(comUserChgMenu);
        } else {
            //사용자 정의 메뉴 merge
            comUserChgMenuService.mergeComUserChgMenu(comUserChgMenu);
        }

        //사용자 권한 개별 버튼 정의 저장
        comUserChgMenuBtnService.saveComUserChgMenuBtnList(comUserChgMenu.getComUserChgMenuBtns(), getUserContext(request).getUserId());
        return comUserChgMenu;
    }

    /**
     * 사용자 조회 모달
     *
     * @param model
     * @return
     */
    @GetMapping("/comUserPopList")
    public String comUserPopList(Model model) {
        return "views/sysm/com/usrp/comUserPopList";
    }

    /**
     * 데이터 조회 모달
     *
     * @param userId  사번
     * @param roleId  권한id
     * @param datRangCd 데이터 조회 범위
     * @return
     */
    @GetMapping({"/userDataRolePopup/{userId}/{roleId}/{datRangCd}"})
    public String userDataRolePopup(@PathVariable Optional<String> userId, @PathVariable Optional<String> roleId, @PathVariable Optional<String> datRangCd, Model model) {
        model.addAttribute("userId", userId.orElse(null))
                .addAttribute("roleId", roleId.orElse(null))
                .addAttribute("datRangCd", datRangCd.orElse(null));
        return "views/sysm/sec/usr/userDataRolePopup";
    }

    /**
     * Role define 모달
     *
     * @param userId  사번
     * @param roleId  권한id
     * @return
     */
    @GetMapping({"/userResponsibilityPopup/{userId}/{roleId}"})
    public String userDataRolePopup(@PathVariable Optional<String> userId, @PathVariable Optional<String> roleId, Model model) {
        model.addAttribute("userId", userId.orElse(null))
                .addAttribute("roleId", roleId.orElse(null));
        return "views/sysm/sec/usr/userResponsibilityPopup";
    }

    /**
     * 사용자 부여된 현장 조회
     *
     * @param comUser
     * @return
     */
    @GetMapping(value = "/userEdit/gridUserDeptList")
    public @ResponseBody
    List<CamelCaseMap> gridUserDeptList(ComUser comUser) {
        return comUserService.getUserDeptList(comUser);
    }

    @PostMapping(value = "/userEdit/gridUserDeptSaveList")
    public @ResponseBody
    UserDept gridUserDeptSaveList(UserDept userDept) {
        comUserService.saveUserDeptList(userDept.getUserDepts(), userDept.getUserId());
        return userDept;
    }

    @GetMapping(value = "/agree")
    public String agree(Model model){

        return "views/test/pwaTest";
    }
}
