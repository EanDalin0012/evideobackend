package com.evideo.evideobackend.unsecur.service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;

public interface VideoSourceService {
    JsonObject inquiryVideoSource(int id) throws ValidatorException;
    JsonObjectArray inquiryVideoSourceByVDId(int vdId) throws ValidatorException;
}
