package com.evideo.evideobackend.core.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    JsonObject loadUserByName(JsonObject param);
    JsonObject authorizationUser(JsonObject jsonObject);
    int addNewUser(JsonObject jsonObject) throws ValidatorException;
    int resetPassword(JsonObject jsonObject) throws ValidatorException;
    int count();
    int updateUserInfo(JsonObject jsonObject) throws ValidatorException;
    JsonObject inquiryUserInfoByID(JsonObject param) throws ValidatorException;
    JsonObjectArray read(JsonObject jsonObject);
    JsonObject  authorizationByUserId(JsonObject jsonObject) throws ValidatorException;
    int deleteUser(JsonObject jsonObject) throws ValidatorException;
    int enableStatus(JsonObject jsonObject) throws ValidatorException;
    int changePassword(JsonObject jsonObject) throws ValidatorException;
    JsonObject inquiryUserById(JsonObject jsonObject) throws ValidatorException;
}
