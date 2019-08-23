package com.henc.cdrs.common.util;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.moyosoft.exchange.Exchange;
import com.moyosoft.exchange.ExchangeServiceException;
import com.moyosoft.exchange.item.BodyType;
import com.moyosoft.exchange.mail.ExchangeMail;



@Component
public class MailUtils {
   
	@Value("${henc.exchange.sender.id}")  
	private String exchangeId;
	
	@Value("${henc.exchange.sender.password}")  
	private String exchangePw;
	
	@Value("${henc.exchange.sender.url}")  
	private String exchangeUrl;
	
	private Exchange exchange = null;
	
    /**
     * 
     * 익스체인지 메일 발송을 위한 Util
     *   
     * @param subject 메일제목
     * @param toRecipients 수신자 (복수인 경우 델리미터 ,로 붙여서 전달 aaa@somehost.com, bbb@somehost..com)
     * @param ccRecipients 참조자 (복수인 경우 델리미터 ,로 붙여서 전달 ccc@somehost.com, ddd@somehost..com)
     * @param body 메일본문
     * @throws ExchangeServiceException
     */
	public void SendMail(String subject, String toRecipients, String ccRecipients, String body) throws ExchangeServiceException{
		
		exchange = new Exchange(exchangeUrl, exchangeId, exchangePw);
		
		ExchangeMail mail = exchange.createMail();
			
        String[] toArry = StringUtils.split(toRecipients,',');
        String[] ccArry = StringUtils.split(ccRecipients,',');
		
        java.util.List<String> toList = new ArrayList<String>(Arrays.asList(toArry));
        java.util.List<String> cclist = new ArrayList<String>(Arrays.asList(ccArry));
		
    	mail.setToRecipients(toList);
		mail.setSubject(subject);
		mail.setCcRecipients(cclist);
		mail.setBody(body , BodyType.Html);
			
	
		mail.send();
		
	}
}