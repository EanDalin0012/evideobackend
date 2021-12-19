package com.evideo.evideobackend.adminlte.service.impl;

import com.evideo.evideobackend.adminlte.dao.MovieDetailsDao;
import com.evideo.evideobackend.adminlte.service.MovieDetailsService;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class MovieDetailsServiceImpl implements MovieDetailsService {
    final MovieDetailsDao movieDetailsDao;

    MovieDetailsServiceImpl(MovieDetailsDao movieDetailsDao) {
        this.movieDetailsDao = movieDetailsDao;
    }

    @Override
    public int create(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "vdTypeId","subVdTypeId", "status", "createAt", "userId");
        return this.movieDetailsDao.create(jsonObject);
    }

    @Override
    public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject,  "vdId");
        return this.movieDetailsDao.read(jsonObject);
    }

    @Override
    public int update(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "vdTypeId", "subVdTypeId","status", "modifyAt", "userId");
        return this.movieDetailsDao.update(jsonObject);
    }

    @Override
    public int delete(JsonObject jsonObject) throws ValidatorException {
        return 0;
    }

    @Override
    public int count() {
        return this.movieDetailsDao.count() + 1;
    }

    @Override
    public JsonObject inquiry(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject,  "vdTypeId", "subVdTypeId");
        return this.movieDetailsDao.inquiry(jsonObject);
    }
}
