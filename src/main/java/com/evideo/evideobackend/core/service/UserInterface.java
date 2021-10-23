package com.evideo.evideobackend.core.service;


import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;

public interface UserInterface {
    JsonObject loadUserByName(JsonObject param) throws ValidatorException;
    JsonObject authorizationUser(JsonObject jsonObject) throws ValidatorException;
    JsonObject  authorizationByUserId(JsonObject jsonObject) throws ValidatorException;
    int addNewUser(JsonObject jsonObject) throws ValidatorException;
    int resetPassword(JsonObject jsonObject) throws ValidatorException;
    int count();
    int updateUserInfo(JsonObject jsonObject) throws ValidatorException;
    JsonObject inquiryUserInfoByID(JsonObject param) throws ValidatorException;
    JsonObjectArray read(JsonObject jsonObject) throws ValidatorException;
    int deleteUser(JsonObject jsonObject) throws ValidatorException;
    int enableStatus(JsonObject jsonObject) throws ValidatorException;
    int changePassword(JsonObject jsonObject) throws ValidatorException;
}
