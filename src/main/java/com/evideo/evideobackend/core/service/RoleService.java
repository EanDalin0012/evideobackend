package com.evideo.evideobackend.core.service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;

public interface RoleService {
    JsonObjectArray read(JsonObject jsonObject) throws ValidatorException;
}
