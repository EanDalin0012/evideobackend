package com.evideo.evideobackend.core.service.implement;

import com.evideo.evideobackend.core.dao.AuthorizationDao;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.AuthorizationService;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImplement implements AuthorizationService {

    final AuthorizationDao authorizationDao;
    AuthorizationServiceImplement(AuthorizationDao authorizationDao) {
        this.authorizationDao = authorizationDao;
    }

    @Override
    public int addAuthorizationAccess(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "userId", "authorityId");
        return this.authorizationDao.addAuthorizationAccess(jsonObject);
    }
}
