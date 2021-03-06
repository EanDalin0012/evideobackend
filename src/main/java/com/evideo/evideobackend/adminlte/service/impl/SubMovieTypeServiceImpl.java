package com.evideo.evideobackend.adminlte.service.impl;

import com.evideo.evideobackend.adminlte.dao.SubMovieTypeDao;
import com.evideo.evideobackend.adminlte.service.CRUDService;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class SubMovieTypeServiceImpl implements CRUDService {
    static Logger log = Logger.getLogger(SubMovieTypeServiceImpl.class.getName());

    final SubMovieTypeDao subMovieTypeDao;
    SubMovieTypeServiceImpl(SubMovieTypeDao movieTypeDao) {
        this.subMovieTypeDao = movieTypeDao;
    }

    @Override
    @PreAuthorize("hasAuthority('Setting_Sub_Movie_Type_Create')")
    public int create(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "vdSubType", "status", "createAt", "userId");
        return this.subMovieTypeDao.create(jsonObject);
    }

    @Override
    @PreAuthorize("hasAuthority('Setting_Sub_Movie_Type_Read')")
    public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject,  "status");
        return this.subMovieTypeDao.read(jsonObject);
    }

    @Override
    @PreAuthorize("hasAuthority('Setting_Sub_Movie_Type_Update')")
    public int update(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "vdSubType", "status", "modifyAt", "userId");
        return this.subMovieTypeDao.update(jsonObject);
    }

    @Override
    @PreAuthorize("hasAuthority('Setting_Sub_Movie_Type_Delete')")
    public int delete(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "status", "modifyAt", "userId");
        return this.subMovieTypeDao.delete(jsonObject);
    }

    @Override
    public int count() {
        return this.subMovieTypeDao.count() + 1;
    }
}
