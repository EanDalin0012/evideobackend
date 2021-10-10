package com.evideo.evideobackend.adminlte.service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;

public interface MovieDetailsService extends CRUDService {
    JsonObject inquiry(JsonObject jsonObject) throws ValidatorException;
}
