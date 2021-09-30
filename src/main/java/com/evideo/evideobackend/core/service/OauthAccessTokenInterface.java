package com.evideo.evideobackend.core.service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.exception.ValidatorException;

public interface OauthAccessTokenInterface {
    int deleteOauthAccessTokenByUserName(JsonObject jsonObject) throws ValidatorException;
    JsonObject getClientIDUserName(JsonObject jsonObject);
}
