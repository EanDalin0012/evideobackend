package com.evideo.evideobackend.unsecur.service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.exception.ValidatorException;

public interface VideoService {
    JsonObject inquiryVideoSource(int id) throws ValidatorException;
}
