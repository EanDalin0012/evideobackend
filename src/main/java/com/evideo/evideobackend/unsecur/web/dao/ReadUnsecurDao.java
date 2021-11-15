package com.evideo.evideobackend.unsecur.web.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;

public interface ReadUnsecurDao {
	JsonObjectArray read(JsonObject jsonObject);
}
