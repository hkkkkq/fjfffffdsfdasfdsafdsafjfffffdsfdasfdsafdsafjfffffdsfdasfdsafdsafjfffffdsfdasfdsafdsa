package com.henc.cdrs.sysm.sec.usr.repository;

import org.apache.ibatis.annotations.Mapper;

import com.henc.cdrs.sysm.sec.usr.model.ComUserChgMenu;
@Mapper
public interface ComUserChgMenuMapper {

	void mergeComUserChgMenu(ComUserChgMenu comUserChgMenu);

	void deleteComUserChgMenu(ComUserChgMenu comUserChgMenu);

}
