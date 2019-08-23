package com.henc.cdrs.partners.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.henc.cdrs.common.config.CdrsProperties;
import com.henc.cdrs.common.notification.model.MmsMsg;
import com.henc.cdrs.common.notification.repository.MmsMsgMapper;
import com.henc.cdrs.coprcp.model.RegReqSend;
import com.henc.cdrs.coprcp.repository.RegReqSendMapper;
import com.henc.cdrs.partners.users.model.PrtnInfo;
import com.henc.cdrs.partners.users.model.Users;
import com.henc.cdrs.partners.users.repository.PartnerUsersMapper;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;
import com.henc.dream.domain.IBSheetRowStatus;
import com.henc.dream.exception.UnsupportedRowTypeException;
import com.henc.dream.util.CamelCaseMap;

@Transactional
@Service
public class PartnerUsersService {

    @Autowired
    private PartnerUsersMapper partnerUsersMapper;

    @Autowired
    private MmsMsgMapper mmsMsgMapper;

    @Autowired
    private RegReqSendMapper regReqSendMapper;

    @Autowired
    private CdrsProperties props;

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    MessageSource messageSource;

    @Autowired
    HttpServletRequest request;

    public List<CamelCaseMap> selectUsersGrdList(Users users) {
        return partnerUsersMapper.selectUsersGrdList(users);
    }

    public void savePartnerUsersList(List<Users> userses, String deptCd ) {
        if (CollectionUtils.isNotEmpty(userses)) {
            for (Users users : userses) {
                users.setDeptCd(deptCd);
                switch (users.getRowStatus()) {
                    case IBSheetRowStatus.INSERTED:
                        String userIdSeq = partnerUsersMapper.selectUserSeq();

                        users.setUserId(userIdSeq);

                        partnerUsersMapper.insertPartnerUsers(users);
                        partnerUsersMapper.insertPartnerUserDept(users);
                        break;
                    case IBSheetRowStatus.UPDATED:
                        partnerUsersMapper.updatePartnerUsers(users);
                        break;
                    case IBSheetRowStatus.DELETED:

                        if(users.getUserId() != null) {
                            partnerUsersMapper.deletePartnerUserDept(users);
                            partnerUsersMapper.deletePartnerUsers(users);
                        }

                        break;
                    default:
                        throw new UnsupportedRowTypeException(users.getRowStatus());
                }
            }
        }
    }

    public List<CamelCaseMap> savePartnerCheck(List<Users> userses) {
        List<CamelCaseMap> dupChk = new ArrayList<>();
        dupChk = partnerUsersMapper.selectDupCheck();

        List<CamelCaseMap> errList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(userses)) {

            for (Users users : userses) {
                for (int i=1; i<dupChk.size(); i++) {
                    String getUserId = users.getUserId();
                    String chkUserId = dupChk.get(i).getString("userId");

                    String getTlno = users.getTlno();
                    String chkTlno = (String) dupChk.get(i).get("tlno");

                    if(getUserId == null) {
                        getUserId = " ";
                    }

                    if(!getUserId.equals(chkUserId)) {
                        if( getTlno.equals(chkTlno) ){
                            CamelCaseMap errMap = new CamelCaseMap();

                            errMap.put("key", dupChk.get(i).get("tlno"));
                            errMap.put("name", dupChk.get(i).get("name"));
                            errMap.put("userId", dupChk.get(i).get("userId"));
                            errMap.put("corpKor", dupChk.get(i).get("corpKor"));
                            errMap.put("errNm", "협력사 : " + dupChk.get(i).get("corpKor") + ", 사용자 : " + dupChk.get(i).get("name")  + "이 입력한 전화번호를 사용중입니다.");
                            errList.add(errMap);

                            //System.out.println("RESULT :: " + getTlno.equals(chkTlno));
                            System.out.println("RESULT :: " + i);
                            System.out.println("RESULT :: " + dupChk.get(i).get("tlno"));
                            System.out.println("RESULT :: " + dupChk.get(i).get("name"));
                        }
                    }
                }
            }
        }

        return errList;
    }




    public List<CamelCaseMap> selectPartnerPopList(PrtnInfo prtnInfo) {
        return partnerUsersMapper.selectPartnerPopList(prtnInfo);
    }

    /**
     * MMS SEND
     * @param userDetail
     * @param phone
     * @param subject
     * @param msg
     */
    public int mmsSend(UserDetail userDetail, String phone, String subject, String msg){
        MmsMsg mmsMsg = new MmsMsg();

        mmsMsg.setId(userDetail.getUserId());
        mmsMsg.setPost(userDetail.getDeptCd());
        mmsMsg.setPhone(phone);
        mmsMsg.setMsg(msg);
        mmsMsg.setSubject(subject);
        mmsMsg.setCallback(props.getCallbackTelNo()); //발신자 번호

        mmsMsg.setStatus("0");
        mmsMsg.setType("0");

        return mmsMsgMapper.insertMmsMsg(mmsMsg);
    }


    public void invitePartner(List<Users> userses, UserDetail userdetail) {
        if (CollectionUtils.isNotEmpty(userses)) {
            RegReqSend regReqSend = new RegReqSend();

            for (Users users : userses) {
                switch (users.getRowStatus()) {
                    case IBSheetRowStatus.UPDATED:

                        regReqSend.setSendId(RandomStringUtils.randomAlphanumeric(7));

                        /*추가 사용자 정보*/
                        regReqSend.setDeptCd(userdetail.getDeptCd());
                        regReqSend.setUserId(users.getUserId());
                        regReqSend.setCoprcpNo(users.getCoprcpNo());
                        regReqSend.setTlno(users.getTlno());

                        StringBuffer addrUrl = request.getRequestURL();
                        //System.out.println(" TEST 5 : " + addrUrl);
                        regReqSendMapper.insertRegReqSend(regReqSend);

                        /*
                        url = request.getScheme()
                                + "://"
                                + request.getServerName()
                                + ":"
                                + request.getServerPort()
                                + request.getRequestURI();
                        */

                        String serverUrl = request.getScheme() + "://"
                                         + request.getServerName() + ":"
                                         + request.getServerPort() + "/partnerConfirm/partnerReg?info=" + regReqSend.getSendId();

                        String [] arg = {serverUrl};
                        String title = messageSource.getMessage("sms.partner.regi.title", null, Locale.getDefault());
                        String message = messageSource.getMessage("sms.partner.regi", arg, Locale.getDefault());

                        mmsSend(userdetail, regReqSend.getTlno(), title, message);

                        break;
                    default:
                        throw new UnsupportedRowTypeException(users.getRowStatus());
                }
            }
        }
    }
}
