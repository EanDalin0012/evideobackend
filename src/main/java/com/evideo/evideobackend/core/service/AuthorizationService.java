package com.evideo.evideobackend.core.service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.exception.ValidatorException;

public interface AuthorizationService {
    int addAuthorizationAccess(JsonObject jsonObject) throws ValidatorException;
    int deleteAuthorizationAccessByUserId(JsonObject jsonObject) throws ValidatorException;
}
