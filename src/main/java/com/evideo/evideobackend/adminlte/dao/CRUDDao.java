package com.evideo.evideobackend.adminlte.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;

public interface CRUDDao {
    int create(JsonObject jsonObject);
    JsonObjectArray read(JsonObject jsonObject);
    JsonObjectArray inquiryByVdId(JsonObject jsonObject);
    int update(JsonObject jsonObject);
    int delete(JsonObject jsonObject);
    int count();
}
