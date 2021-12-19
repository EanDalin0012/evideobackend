package com.evideo.evideobackend.adminlte.service.impl;

import com.evideo.evideobackend.adminlte.dao.MovieTypeDao;
import com.evideo.evideobackend.adminlte.service.CRUDService;
import com.evideo.evideobackend.adminlte.service.MovieTypeService;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MovieTypeServiceImpl implements CRUDService, MovieTypeService {
    static Logger log = Logger.getLogger(MovieTypeServiceImpl.class.getName());

    final MovieTypeDao movieTypeDao;
    MovieTypeServiceImpl(MovieTypeDao movieTypeDao) {
        this.movieTypeDao = movieTypeDao;
    }

    @Override
    @PreAuthorize("hasAuthority('Setting_Movie_Type_Create')")
    public int create(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "vdType", "status", "createAt", "userId", "settingClient");
        return this.movieTypeDao.create(jsonObject);
    }

    @Override
    @PreAuthorize("hasAuthority('Setting_Movie_Type_Read')")
    public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject,  "status");
        return this.movieTypeDao.read(jsonObject);
    }

    @Override
    @PreAuthorize("hasAuthority('Setting_Movie_Type_Update')")
    public int update(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "vdType", "status", "modifyAt", "userId");
        return this.movieTypeDao.update(jsonObject);
    }

    @Override
    @PreAuthorize("hasAuthority('Setting_Movie_Type_Delete')")
    public int delete(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "status", "modifyAt", "userId");
        return this.movieTypeDao.delete(jsonObject);
    }

    @Override
    public int count() {
        return this.movieTypeDao.count() + 1;
    }

    @Override
    public int updateStatusYN(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "settingClient", "modifyAt", "userId");
        return this.movieTypeDao.updateStatusYN(jsonObject);
    }
}
