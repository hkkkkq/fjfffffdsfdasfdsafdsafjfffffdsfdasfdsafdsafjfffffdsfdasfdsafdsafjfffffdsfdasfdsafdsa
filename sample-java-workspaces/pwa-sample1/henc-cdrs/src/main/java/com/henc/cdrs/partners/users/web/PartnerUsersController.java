package com.henc.cdrs.partners.users.web;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.partners.users.model.PrtnInfo;
import com.henc.cdrs.partners.users.model.Users;
import com.henc.cdrs.partners.users.service.PartnerUsersService;
import com.henc.cdrs.sysm.rnr.model.ComRole;
import com.henc.cdrs.sysm.rnr.service.ComRoleService;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.cdrs.sysm.sec.usr.service.ComUserService;
import com.henc.dream.util.CamelCaseMap;

@Controller
@RequestMapping("/partners/users")
public class PartnerUsersController extends BaseController {

    @Autowired
    private PartnerUsersService partnerUsersService;

    @Autowired
    ComUserService comUserService;

    @Autowired
    ComRoleService comRoleService;

    @Autowired
    HttpServletRequest request;

    /* 협력사 관리 화면 */
    @GetMapping("/")
    public String list(@PathVariable Optional<String> userId, Model model) {

        model.addAttribute("userId", userId.orElse(null));
        model.addAttribute("roles", comRoleService.selectComRoleList(new ComRole()));

        return "views/partners/users/users";
    }

    /* 협력사 사용자 조회  */
    @GetMapping("/gridList")
    public @ResponseBody
    List<CamelCaseMap> gridList(Users users) {
        UserDetail userDetail = getUserContext(request);
        String deptCd = userDetail.getDeptCd();
        users.setDeptCd(deptCd);
        return partnerUsersService.selectUsersGrdList(users);
    }

    /* 협력사 사용자 조회  */
    @GetMapping("/gridCheck")
    public @ResponseBody
    List<CamelCaseMap> gridCheck(Users users) {
        return partnerUsersService.savePartnerCheck(users.getUserses());
    }

    /* 협력사 사용자 등록 저장 */
    @PostMapping(value = "/gridSave")
    public @ResponseBody Users gridSave(Users users) {
        partnerUsersService.savePartnerUsersList(users.getUserses(), getUserContext(request).getDeptCd());
        return users;
    }

    /* 협력사 팝업 화면 */
    @GetMapping({"/partnerListModal", "/partnerListModal/{searchExceptYn}", "/partnerListModal/{searchExceptYn}/{searchPartnerNm}"})
    public String partnerListModal(@PathVariable(required = false) String searchExceptYn, @PathVariable(required = false) String searchPartnerNm, Model model) {
        return "views/partners/users/partnerListModal";
    }

    /* 협력사 계약 현황 팝업 조회 */
    @GetMapping(value = "/gridParterPopList")
    public @ResponseBody List<CamelCaseMap> gridPrtnPopList(PrtnInfo prtnInfo) {
        UserDetail userDetail = getUserContext(request);
        String deptCd = userDetail.getDeptCd();
        prtnInfo.setDeptCd(deptCd);
        return partnerUsersService.selectPartnerPopList(prtnInfo);
    }

    /* 협력사 사용자 현장등록 팝업 화면 */
    @GetMapping({"/partnerDeptListModal", "/partnerDeptListModal/{searchDeptNm}"})
    public String deptListModal(@PathVariable(required = false) String searchDeptNm, Model model) {
        return "views/partners/users/partnerDeptListModal";
    }

    @PostMapping("/partnerUrl")
    public @ResponseBody Users partnerUrlList(Users users, HttpServletRequest request){
        UserDetail userDetail = getUserContext(request);
        partnerUsersService.invitePartner(users.getUserses(), userDetail);
        return users;
    }
}
