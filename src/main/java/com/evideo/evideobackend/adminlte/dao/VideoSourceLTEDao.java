package com.evideo.evideobackend.adminlte.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoSourceLTEDao extends CRUDDao {
    int inquiryPart(JsonObject jsonObject);
}
