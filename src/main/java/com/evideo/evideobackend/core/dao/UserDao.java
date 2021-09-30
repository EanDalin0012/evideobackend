package com.evideo.evideobackend.core.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.exception.ValidatorException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    JsonObject loadUserByName(JsonObject param);
    int addNewUser(JsonObject jsonObject) throws ValidatorException;
    int resetPassword(JsonObject jsonObject) throws ValidatorException;
    int count();
    int updateUserInfo(JsonObject jsonObject) throws ValidatorException;
    JsonObject inquiryUserInfoByID(JsonObject param) throws ValidatorException;
}
