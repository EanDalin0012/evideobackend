package com.evideo.evideobackend.adminlte.service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.exception.ValidatorException;

public interface MovieTypeService {
    int updateStatusYN(JsonObject jsonObject) throws ValidatorException;
}
