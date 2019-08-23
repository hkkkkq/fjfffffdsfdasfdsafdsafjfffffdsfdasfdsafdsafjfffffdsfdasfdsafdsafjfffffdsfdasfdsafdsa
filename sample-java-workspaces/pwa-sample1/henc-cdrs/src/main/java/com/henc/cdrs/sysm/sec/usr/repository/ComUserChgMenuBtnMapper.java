package com.henc.cdrs.sysm.sec.usr.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.sysm.sec.usr.model.ComUserChgMenuBtn;

@Mapper
public interface ComUserChgMenuBtnMapper {

	List<CamelCaseMap> getComUserChgMenuBtnList(ComUserChgMenuBtn comUserChgMenuBtn);

	void mergeComUserChgMenuBtnList(ComUserChgMenuBtn comUserChgMenuBtn);
	
	void deleteComUserChgMenuBtnList(ComUserChgMenuBtn comUserChgMenuBtn);

}
