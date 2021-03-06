package com.evideo.evideobackend.core.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceInfoDao {
    int save(JsonObject jsonNode);
    int updateDeviceInfo(JsonObject jsonNode);
    JsonObject inquiry(JsonObject jsonNode);
    JsonObjectArray inquiryByUserAgent(JsonObject jsonNode);
    int count();
    int deleteDeviceInfo(JsonObject jsonNode);
}
