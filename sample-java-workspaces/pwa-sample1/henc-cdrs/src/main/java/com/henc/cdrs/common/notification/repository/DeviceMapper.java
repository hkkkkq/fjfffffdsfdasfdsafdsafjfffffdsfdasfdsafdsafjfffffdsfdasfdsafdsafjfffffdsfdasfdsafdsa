package com.henc.cdrs.common.notification.repository;

import com.henc.cdrs.common.notification.model.Device;
import com.henc.dream.util.CamelCaseMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by beoms(lbg6848@sdcit.co.kr) on 2019-07-01
 * Corporation : SDCIT(주)
 * HomePage : http://www.sdcit.co.kr
 */
@Mapper
public interface DeviceMapper {
    List<Device> selectDevice(@Param("userId") String userId, @Param("token") String token);

    int insertDevice(Device device);
    int updateDevice(Device device);

}
