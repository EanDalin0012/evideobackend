package com.evideo.evideobackend.core.service.impl;

import com.evideo.evideobackend.core.dao.OauthAccessTokenDao;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.service.OauthAccessTokenInterface;
import org.springframework.stereotype.Service;

@Service
public class OauthAccessTokenService implements OauthAccessTokenInterface {
    final OauthAccessTokenDao oauthAccessTokenDao;
    OauthAccessTokenService(OauthAccessTokenDao oauthAccessTokenDao) {
        this.oauthAccessTokenDao = oauthAccessTokenDao;
    }

    @Override
    public int deleteOauthAccessTokenByUserName(JsonObject jsonObject) {
        return this.oauthAccessTokenDao.deleteOauthAccessTokenByUserName(jsonObject);
    }

    @Override
    public JsonObject getClientIDUserName(JsonObject jsonObject) {
        return this.oauthAccessTokenDao.getClientIDUserName(jsonObject);
    }

}
