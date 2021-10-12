package com.evideo.evideobackend.unsecur.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoSourceDao {
    JsonObject inquiryVideoSource(JsonObject jsonObject);
    JsonObject inquirySourceVideo(JsonObject jsonObject);
    JsonObjectArray inquiryVideoSourceByVDId(JsonObject jsonObject) throws ValidatorException;
}
