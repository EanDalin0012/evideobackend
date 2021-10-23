package com.evideo.evideobackend.core.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleDao {
    JsonObjectArray read(JsonObject jsonObject) throws ValidatorException;
}
