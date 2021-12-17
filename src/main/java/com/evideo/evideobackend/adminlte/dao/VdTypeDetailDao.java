package com.evideo.evideobackend.adminlte.dao;

import org.apache.ibatis.annotations.Mapper;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;

@Mapper
public interface VdTypeDetailDao {
	int create(JsonObject jsonObject);
	JsonObjectArray read();
	JsonObjectArray readVdSubType(JsonObject jsonObject);
	JsonObject readObj(JsonObject jsonObject);
	int update(JsonObject jsonObject);
    int delete(JsonObject jsonObject);
    int count();
}
