package com.evideo.evideobackend.core.service;


import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;

public interface DeviceInfoInterface {
    int save(JsonObject param);
    int updateDeviceInfo(JsonObject param);
    JsonObject inquiry(JsonObject param);
    JsonObjectArray inquiryByUserAgent(JsonObject param) throws ValidatorException;
    int count();
    int deleteDeviceInfo(JsonObject jsonObject) throws ValidatorException;

}
