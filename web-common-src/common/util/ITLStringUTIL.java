package com.kt.kkos.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;

import com.kt.kkos.exception.ITLBusinessException;

public class ITLStringUTIL {
	
	
	static public String nvl(String str, String reStr)
	{
	    if(ITLStringUTIL.isNullOrEmpty(str))
	    {
	        return reStr;
	    }
	    
	    return str;
	}
	
	static public String stringToAstr(String funcName, String value, String paramCd)
    {
	    //System.out.println("by chk paramCd=" + paramCd);
	    if(ITLStringUTIL.isNullOrEmpty(funcName))
	    {
	        return value;
	    }
	    if(ITLStringUTIL.isNullOrEmpty(paramCd))
	    {
            if(funcName.toUpperCase().contains("SWTCPRMTSBST") || 
                    funcName.toUpperCase().contains("SWTCMOTSBST") ||
                    funcName.toUpperCase().contains("FEATURES"))
            {
                return featuresToAstr(value);
            }
            else
            {
                ITLMaskRule maskRule = (ITLMaskRule) ApplicationContextHolder.getContext().getBean("ITLMaskRuleAdapter");
                String rule = (String)(maskRule.getFildMaskMap()).get(funcName.toUpperCase().replaceAll("_", "").replaceAll("-", ""));
                
                if(ITLStringUTIL.isNullOrEmpty(rule))
                {
                    return value;
                }
                else
                {
                    return maskStr(value, rule);
                }
            }
	    }
	    else
	    {
	        return paramToAstr(paramCd, value);
	    }
    }
	
	static public String lpad(String str, int size, char pad)
	{
		byte b[] = null;
		byte p[] = new byte[size];
		
		if(str == null)
		{
			b = new byte[0];
		}
		else
		{
			try
			{
				b = str.getBytes(ITLKeyUTIL.ITL_NEOSS_SOCKET_DECODE_CHARSET);
			}
			catch(Exception e)
			{
				return "";
			}
		}
		
		if(b.length > size)
		{
			return str;
		}
		
		int padCnt = size - b.length;
		
		for(int i = 0; i < padCnt; i++)
		{
			p[i] = (byte)pad;
		}
		
		for(int i = 0; i < b.length; i++)
		{
			p[padCnt + i] = b[i];
		}
		
		try
		{
			return new String(p, ITLKeyUTIL.ITL_NEOSS_SOCKET_DECODE_CHARSET);
		}
		catch(Exception e)
		{
			return new String("");
		}
	}
	
	static public boolean isNullOrEmpty(String str)
	{
		if(str == null || str.isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	static public String getSysDate()
	{
		SimpleDateFormat sf = new SimpleDateFormat(ITLKeyUTIL.ITL_DATE_FORMAT_DATETIME);
		
		Date dt = new Date(); 
		return sf.format(dt);
	}
	
	static public String getSysDate(String format)
	{
		SimpleDateFormat sf = new SimpleDateFormat(format);
		
		Date dt = new Date(); 
		return sf.format(dt);
	}
	
	static public String getSysDateForMiliSec()
	{
		String str="";
		Calendar ca = Calendar.getInstance();
		
		str = ITLStringUTIL.getSysDate() + StringUtil.rpad(""+ca.get(Calendar.MILLISECOND), 3, "0");
		return str;
	}
	
	static public String getSysTimeForMiliSec()
	{
		String str="";
		Calendar ca = Calendar.getInstance();
		
		str = ITLStringUTIL.getSysDate("HHmmss") + StringUtil.rpad(""+ca.get(Calendar.MILLISECOND), 3, "0");
		return str;
	}
	
	static public boolean isDateFormat(String str, String format)
	{
	    try
	    {
            SimpleDateFormat df = new SimpleDateFormat(format);
            
            String chk = df.format(df.parse(str));
            
            if(str.equalsIgnoreCase(chk))
            {
                return true;
            }
            else
            {
                return false;
            }
	    }
	    catch(ParseException e)
	    {
	        return false;
	    }
	    catch(Exception e)
	    {
	        return false;
	    }
	}
	
	static public String modifyTimeString(String strTime, int field, int amount)
	{
		String str="";
		
		try
		{
			Calendar ca = Calendar.getInstance();
	
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	
			ca.setTime(df.parse(strTime));
			ca.add(field, amount);
			
			str = df.format(ca.getTime());
		}
		catch(ParseException e)
		{
			return strTime;
		}
		
		return str;
	}
	
	static public String replaceNull(String str)
	{
		String data = "";
		
		if(ITLStringUTIL.isNullOrEmpty(str))
		{
			return null;
		}
		
		char[] chArray = str.toCharArray();
		
		for(int i = 0; i < chArray.length; i++)
		{
			if(chArray[i] != 0x00)
			{
				data = data + chArray[i];		// NOPMD
			}
		}
		
		return data;
	}
	
	static public String subStr(String str, int startIdx, int endIdx)
	{
		if(ITLStringUTIL.isNullOrEmpty(str))
		{
			return "";
		}
		else if(str.length() < endIdx)
		{
			return "";
		}
		else
		{
			return str.substring(startIdx, endIdx);
		}
	}
	
	static public String subStr(String str, int idx)
	{
		if(ITLStringUTIL.isNullOrEmpty(str))
		{
			return "";
		}
		else if(str.length() < idx)
		{
			return "";
		}
		else
		{
			return str.substring(idx);
		}
	}

	static public String NullToEmpty(String str)
	{
		if(str == null)
		{
			return "";
		}
		else 
		{
			return str;
		}
	}
	
	static public String chnageDateFormat(String str, String prev_format, String new_format)
	{
		if(ITLStringUTIL.isNullOrEmpty(str) ||
				ITLStringUTIL.isNullOrEmpty(prev_format) ||
				ITLStringUTIL.isNullOrEmpty(new_format))
		{
			return null;
		}
		SimpleDateFormat prevSf = new SimpleDateFormat(prev_format);
		SimpleDateFormat newSf = new SimpleDateFormat(new_format);
		
		try
		{
			Date dt = prevSf.parse(str);
			
			return newSf.format(dt);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
    static public String hexString(String str, int nByte)
    {
        StringBuffer hexBuf = new StringBuffer();
        
        for(int i = 0; i < str.length(); i++)
        {
            hexBuf.append(Integer.toHexString(str.charAt(i)));
        }
        
        String retStr = String.format(hexBuf.toString() + "% " + Integer.toString((nByte * 2) + 1) + "d ", 0).substring(0, nByte * 2).replace(" ", "F");
        
        return retStr;
    }

    
    @SuppressWarnings("rawtypes")
    static public String featuresToAstr(String value)
    {
        String makeMsg = value;
        ITLMaskRule maskRule = (ITLMaskRule) ApplicationContextHolder.getContext().getBean("ITLMaskRuleAdapter");
        for(Map.Entry entry: maskRule.getSwtcMaskMap().entrySet())
        {
            String swtcRule = (String)entry.getValue();
            String key = (String)entry.getKey();
            
            if(makeMsg.indexOf(key) >= 0)
            {
                int startIdx = makeMsg.indexOf(key) + 1;
                int keyLen = key.length();
                
                int valueLen = makeMsg.substring(startIdx + keyLen).indexOf("@");
                int afterLen = startIdx + keyLen + valueLen;
                
                String msgValue = value.substring(startIdx+keyLen, startIdx+keyLen+valueLen);
                
                makeMsg = makeMsg.substring(0, startIdx+keyLen) + maskStr(msgValue, swtcRule) + makeMsg.substring(afterLen);
            }
        }
        return makeMsg;
    }
    
    static public String paramToAstr(String paramCd, String value)
    {
        ITLMaskRule maskRule = (ITLMaskRule) ApplicationContextHolder.getContext().getBean("ITLMaskRuleAdapter");
        String paramRule = (String)maskRule.getParamMaskMap().get(paramCd);
        
        if(paramRule == null)
        {
            return value;
        }
        else
        {
            return maskStr(value, paramRule);
        }
    }
        
    static public String maskStr(String str, String rule)
    {
        try
        {

            String maskStr = str;
            
            int len = str.length();
            
            if(str == null || str.isEmpty() == true)
            {
                return str;
            }
            
            String [] ruleList = rule.split("@");
            
            for(int i = 0; i < ruleList.length; i++)
            {
                String [] ruleArr = ruleList[i].split(":");
                
                if(ruleArr.length <= 0 || ruleArr.length > 2)
                {
                    continue;
                }
 
                if(ruleArr[0].substring(0,1).equals("L"))
                {
                    int startIdx = Integer.parseInt(ruleArr[0].substring(1));
                    
                    int endIdx = 0;
                    if(ruleArr.length > 1)
                    {
                        endIdx = Integer.parseInt(ruleArr[1]);
                        if(endIdx > len)
                        {
                            endIdx = len;
                        }
                    }
                    else
                    {
                        endIdx = len;
                    }
                    
                    if(startIdx >= endIdx)
                    {
                        continue;
                    }
                    
                    maskStr = maskStr.substring(0, startIdx) + StringUtil.rpad("", endIdx - startIdx, "*") + maskStr.substring(endIdx, len);
                }
                else if(ruleArr[0].substring(0,1).equals("R"))
                {
                    int startIdx = len - Integer.parseInt(ruleArr[0].substring(1));
                    
                    if(startIdx < 0)
                    {
                        startIdx = 0;
                    }
                    
                    int endIdx = 0;
                    if(ruleArr.length > 1)
                    {
                        endIdx = len - Integer.parseInt(ruleArr[1]);
                        if(endIdx > len)
                        {
                            endIdx = len;
                        }
                    }
                    else
                    {
                        endIdx = len;
                    }

                    if(startIdx >= endIdx)
                    {
                        continue;
                    }
                    
                    maskStr = maskStr.substring(0, startIdx) + StringUtil.rpad("", endIdx - startIdx, "*") + maskStr.substring(endIdx, len);
                }
            }
            return maskStr;
        }
        catch(Exception e)
        {
            ITLBusinessException.printErrorStack(e);
            return str;
        }
    }
    
    @SuppressWarnings("rawtypes")
    static public String msgMasking(String msg, String srvrId, String aplyWrkjob, String maskTypeCd)
    {
        ITLMaskRule maskRule = (ITLMaskRule) ApplicationContextHolder.getContext().getBean("ITLMaskRuleAdapter");
        String maskMsg = msg;
        if(ITLStringUTIL.isNullOrEmpty((maskTypeCd)) ||
                ITLStringUTIL.isNullOrEmpty((srvrId)) ||
                ITLStringUTIL.isNullOrEmpty((aplyWrkjob)) ||
                ITLStringUTIL.isNullOrEmpty((msg)))
        {
            return msg;
        }
        
        HashMap<String, Object> map = maskRule.getTcpMaskMap(srvrId, aplyWrkjob, maskTypeCd);
        
        if(maskTypeCd.equals(ITLKeyUTIL.TCP_GEAR_SBST_MASK_BY_KEY))
        {
            for(Map.Entry entry: map.entrySet())
            {
                String tcpRule = (String)entry.getValue();
                String key = (String)entry.getKey();
                
                String [] arr = tcpRule.split("::");
                
                if(arr.length != 2)
                {
                    continue;
                }
                
                String rule = arr[1];
                String spliter = arr[0];
                
                if(msg.indexOf(key) >= 0)
                {
                    int startIdx = msg.indexOf(key) + 1;
                    int keyLen = key.length();
                    
                    int valueLen = msg.substring(startIdx + keyLen).indexOf(spliter);
                    int afterLen = startIdx + keyLen + valueLen;
                    
                    String msgValue = msg.substring(startIdx+keyLen, startIdx+keyLen+valueLen);
                    
                    maskMsg = maskMsg.substring(0, startIdx+keyLen) + maskStr(msgValue, rule) + maskMsg.substring(afterLen);
                }
            }
            return maskMsg;
        }
        else if(maskTypeCd.equals(ITLKeyUTIL.TCP_GEAR_SBST_MASK_BY_COORD))
        {
            String rule = (String)map.get("RULE");
            
            if(ITLStringUTIL.isNullOrEmpty(rule))
            {
                return msg;
            }
            else
            {
                return maskStr(maskMsg, rule);
            }
        }
        else
        {
            return msg;
        }
        
    }

    
    static public int strMatchCnt(String str, String matchStr)
    {
    	int i = 0;
    	int idx = 0;
    	
    	while((idx = str.indexOf(matchStr, idx)) >= 0)
    	{
    		idx = idx + matchStr.length();
    		i++;
    	}
    	
    	return i;
    }

    static public boolean isDigit(String str)
    {
        if(ITLStringUTIL.isNullOrEmpty(str))
            return false;
        
        char strArr[] = str.toCharArray();
        
        for(int i = 0; i < strArr.length; i++)
        {
            if(strArr[i] < '0' || strArr[i] > '9')
            {
                return false;
            }
        }
        return true;
    }
    
    static public String getInputStream(Message message) throws IOException {
        if (null == message) return null;
        
        // Get the supplied SOAP envelope in the form of an InputStream         
        InputStream is = message.getContent(InputStream.class);
        if (is == null) return null;

        StringBuilder buffer = new StringBuilder();
        try 
        {
             // Cache InputStream so it can be read independently
            CachedOutputStream cos = new CachedOutputStream();
            
            IOUtils.copy(is,cos);
            is.close();
            cos.close();                
            
            /**              
             * If you just want to read the InputStream and not
             * modify it then you just need to put it back where
             * it was using the CXF cached inputstream
             */
            message.setContent(InputStream.class,cos.getInputStream());
            
            int limit = cos.size();
            cos.writeCacheTo(buffer, limit);
        } catch(IOException e){			// NOPMD

        } finally {
            if (null != is) is.close();
        }

        return buffer.toString(); 
    }
    
    /**
	 * <pre>
	 * 객체를 순회하여, DTO(KkosStdVOType), List 객체에 한하여 하위 Field 정보를 Buffer에 담음.
	 * </pre>
	 *
	 * @param subObject
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
    static public String toStringObject(Object subObject){
    		return StringUtil.toString(subObject);
	}
	/**
	 * <pre>
	 * 객체의 원 Interface를 찾기 위함.
	 * </pre>
	 *
	 * @param interfaces
	 * @param type
	 * @return
	 */
    static public boolean findInterfaceType(Class<?>[] interfaces, String type) {
		
		for ( Class<?> ifs : interfaces ) {
			
			if ( ifs.getName().indexOf(type) > -1 ) {
				
				return true;
			}
		}
		
		return false;
	}
}	


