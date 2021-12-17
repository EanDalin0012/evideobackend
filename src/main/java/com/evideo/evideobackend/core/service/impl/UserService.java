package com.evideo.evideobackend.core.service.impl;

import com.evideo.evideobackend.core.dao.UserDao;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.UserInterface;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserInterface {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    final UserDao userDao;

    UserService(UserDao userDao) {
     this.userDao = userDao;
    }

    @Override
    public JsonObject loadUserByName(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "userName");
        return this.userDao.loadUserByName(param);
    }

    @Override
    public JsonObject authorizationUser(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "userName");
        return this.userDao.authorizationUser(jsonObject);
    }

    @Override
    public JsonObject authorizationByUserId(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "userId");
        return this.userDao.authorizationByUserId(jsonObject);
    }

    @Override
    public int addNewUser(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id","password", "userName", "fullName", "dateBirth", "gender", "dateBirth");
        return this.userDao.addNewUser(jsonObject);
    }

    @Override
    public int resetPassword(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id","userName", "password", "isFirstLogin");
        return this.userDao.resetPassword(jsonObject);
    }

    @Override
    public int count() {
        return this.userDao.count() + 1;
    }

    @Override
    public int updateUserInfo(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "userId","fullName", "gender", "dateBirth");
        return this.userDao.updateUserInfo(jsonObject);
    }

    @Override
    public JsonObject inquiryUserInfoByID(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "userID");
        return this.userDao.inquiryUserInfoByID(param);
    }

    @Override
    public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "status", "id");
        return this.userDao.read(jsonObject);
    }

    @Override
    public int deleteUser(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "status", "userId", "modifyAt");
        return this.userDao.deleteUser(jsonObject);
    }

    @Override
    public int enableStatus(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "status", "userId", "modifyAt", "enable");
        return this.userDao.enableStatus(jsonObject);
    }

    @Override
    public int changePassword(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "status", "userId", "modifyAt", "password");
        return this.userDao.changePassword(jsonObject);
    }

	@Override
	public JsonObject inquiryUserById(JsonObject jsonObject) throws ValidatorException {
		ValidatorUtil.validate(jsonObject, "id");
		return this.userDao.inquiryUserById(jsonObject);
	}
}
