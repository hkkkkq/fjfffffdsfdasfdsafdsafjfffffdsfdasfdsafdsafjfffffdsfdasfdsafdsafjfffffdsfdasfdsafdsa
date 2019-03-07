package com.kt.svc1;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SqlMapper {

	public void createConnectionHistory(Request req);

	
}
