package com.henc.cdrs.sysm.sec.auth.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.sysm.sec.auth.security.config.JwtSettings;
import com.henc.cdrs.sysm.sec.auth.security.token.RawAccessJwtToken;

@RestController
public class ApiAuthController extends BaseController {

    private JwtSettings settings;

    @Autowired
    public ApiAuthController(JwtSettings settings) {
        this.settings = settings;
    }

    @RequestMapping("/security/apiAuth")
    public String Auth(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            String tokenSigningKey = settings.getTokenSigningKey();
            String tokenPayload = request.getParameter("accessToken");
            RawAccessJwtToken rawAccessToken = new RawAccessJwtToken(tokenPayload);
            rawAccessToken.parseClaims(tokenSigningKey);    //token 유효 체크             

            return "true";
            
/*            PrintWriter out = response.getWriter();
            
            JSONObject obj = new JSONObject();
            obj.put("name","foo");
            out.print(obj);
            out.flush();
            out.close();  */

        } catch (Exception e) {
            return "false";
        }
    }

}
