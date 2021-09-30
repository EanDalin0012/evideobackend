package com.evideo.evideobackend.core.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthAccessTokenDao {
    int deleteOauthAccessTokenByUserName(JsonObject jsonNode);
    JsonObject getClientIDUserName(JsonObject jsonNode);
}
