package com.evideo.evideobackend.adminlte.service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;

public interface ClientSettingService {
    JsonObjectArray read();
    int insertVideoTypeDt(JsonObject jsonObject) throws ValidatorException;
    int deleteVideoTypeDt(JsonObject jsonObject) throws ValidatorException;
    int count();
}
