package com.evideo.evideobackend.adminlte.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoSourceLTEDao extends CRUDDao {
    int inquiryPart(JsonObject jsonObject);
    JsonObjectArray inquirySchedule(JsonObject jsonObject) throws ValidatorException;
    int updateSchedule(JsonObject jsonObject) throws ValidatorException;
}
