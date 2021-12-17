package com.evideo.evideobackend.adminlte.service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;


public interface VdTypeDetailService {
	int create(JsonObject jsonObject) throws ValidatorException ;
	JsonObjectArray read();
	JsonObjectArray readVdSubType(JsonObject jsonObject) throws ValidatorException;
	JsonObject readObj(JsonObject jsonObject) throws ValidatorException;
	int update(JsonObject jsonObject) throws ValidatorException;
    int delete(JsonObject jsonObject) throws ValidatorException;
    int count();
}
