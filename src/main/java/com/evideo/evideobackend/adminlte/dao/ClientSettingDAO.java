package com.evideo.evideobackend.adminlte.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClientSettingDAO {
    JsonObjectArray read();
    int insertVideoTypeDt(JsonObject jsonObject);
    int deleteVideoTypeDt(JsonObject jsonObject);
    int count();
}
