package com.evideo.evideobackend.core.service;


import com.evideo.evideobackend.core.dto.JsonObject;

public interface DefaultAuthenticationProviderInterface {
    JsonObject getUserObjectByName(JsonObject param) throws Exception;
    JsonObject authenticate(JsonObject jsonObject);
}
