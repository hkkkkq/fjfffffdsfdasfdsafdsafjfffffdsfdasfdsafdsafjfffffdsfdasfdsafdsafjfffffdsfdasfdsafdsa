package com.kt.svc2;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SqlMapper {

	public void createConnectionHistory(Request req);

	
}
