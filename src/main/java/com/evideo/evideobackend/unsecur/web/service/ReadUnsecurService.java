package com.evideo.evideobackend.unsecur.web.service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;

public interface ReadUnsecurService {
	JsonObjectArray read(JsonObject jsonObject) throws ValidatorException;
}
