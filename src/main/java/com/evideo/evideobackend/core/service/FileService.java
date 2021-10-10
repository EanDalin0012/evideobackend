package com.evideo.evideobackend.core.service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.exception.ValidatorException;

public interface FileService {
    int create(JsonObject jsonObject) throws ValidatorException;
    JsonObject readBySourceId(JsonObject jsonObject) throws ValidatorException;
    int count();
}
