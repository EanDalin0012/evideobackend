package com.evideo.evideobackend.core.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorizationDao {
    int addAuthorizationAccess(JsonObject jsonObject);
}
