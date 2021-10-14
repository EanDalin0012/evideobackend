package com.evideo.evideobackend.core.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileDao {
    int create(JsonObject jsonObject);
    int delete(JsonObject jsonObject);
    JsonObject read(JsonObject jsonObject);
    int count();
}
