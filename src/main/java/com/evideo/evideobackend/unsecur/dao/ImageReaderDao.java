package com.evideo.evideobackend.unsecur.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageReaderDao {
    JsonObject inquiryResourcesID(JsonObject param);
}
