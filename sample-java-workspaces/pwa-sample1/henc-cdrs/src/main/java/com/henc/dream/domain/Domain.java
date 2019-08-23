package com.henc.dream.domain;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.sysm.sec.auth.model.UserDetail;

import lombok.Data;
import lombok.Getter;

/**
 * Model class의 상위 객체 역할을하는 클래스로
 * 반복되어 사용되어지는 정보(로그인, 아이피 정보 등)를 추상화하여 제공.
 *
 * @author YongSang
 */
@Data
public class Domain {
    /**
     * 시스템 로그인 아이디
     */
    @Getter(onMethod=@__({@JsonIgnore}))
    private String loginId;

    /**
     * 시스템 로그인 사용자 이름
     */
    @Getter(onMethod=@__({@JsonIgnore}))
    private String loginName;

    @Getter(onMethod=@__({@JsonIgnore}))
    private Date now = new Date();

    @Getter(onMethod=@__({@JsonIgnore}))
    private BaseController controller = new BaseController();

    public Domain() {
        init();
    }

    @JsonIgnore
    public UserDetail getPrincipal() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        return controller.getUserContext(request);
    }

    private void init() {
        try {
            ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = sra.getRequest();
            UserDetail userDetail = controller.getUserContext(request);
            loginId = userDetail.getUserId();
            loginName = userDetail.getName();

        } catch (Exception e) {
            loginId = "SYSTEM";
        }
    }
}