package com.evideo.evideobackend.adminlte.service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.exception.ValidatorException;

public interface VideoSourceLTEService extends CRUDService {
    int inquiryPart(JsonObject jsonObject) throws ValidatorException;
}
