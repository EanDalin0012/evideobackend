package com.evideo.evideobackend.core.service.impl;

import com.evideo.evideobackend.core.dao.AuthorizationDao;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.AuthorizationService;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    final AuthorizationDao authorizationDao;
    AuthorizationServiceImpl(AuthorizationDao authorizationDao) {
        this.authorizationDao = authorizationDao;
    }

    @Override
    public int addAuthorizationAccess(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "userId", "authorityId");
        return this.authorizationDao.addAuthorizationAccess(jsonObject);
    }

    @Override
    public int deleteAuthorizationAccessByUserId(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "userId");
        return this.authorizationDao.deleteAuthorizationAccessByUserId(jsonObject);
    }
}
