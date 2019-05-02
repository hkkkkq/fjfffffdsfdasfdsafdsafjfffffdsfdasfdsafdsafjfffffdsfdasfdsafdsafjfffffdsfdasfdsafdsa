package com.kt.kkos.common.message;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;

import com.kt.kkos.common.message.MessageProcess;
    
public class MessageProcess {
    private static MessageSourceAccessor messageSourceAccessor = null;
    
    public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor){
        MessageProcess.messageSourceAccessor = messageSourceAccessor;
    }
    
    public static String getMessage(String key){
        return messageSourceAccessor.getMessage(key, Locale.getDefault()); //Locale.KOREA);
    }
    
    public static String getMessage(String key, Object[] objs){
        return messageSourceAccessor.getMessage(key, objs, Locale.getDefault());
    }
}
