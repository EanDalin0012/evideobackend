package com.evideo.evideobackend.adminlte.service.implement;

import com.evideo.evideobackend.adminlte.dao.SubMovieTypeDao;
import com.evideo.evideobackend.adminlte.service.CRUDService;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class SubMovieTypeServiceImplement implements CRUDService {
    static Logger log = Logger.getLogger(SubMovieTypeServiceImplement.class.getName());

    final SubMovieTypeDao subMovieTypeDao;
    SubMovieTypeServiceImplement(SubMovieTypeDao movieTypeDao) {
        this.subMovieTypeDao = movieTypeDao;
    }

    @Override
    public int create(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "vdSubType", "status", "createAt", "userId");
        return this.subMovieTypeDao.create(jsonObject);
    }

    @Override
    public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject,  "status");
        return this.subMovieTypeDao.read(jsonObject);
    }

    @Override
    public int update(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "vdSubType", "status", "modifyAt", "userId");
        return this.subMovieTypeDao.update(jsonObject);
    }

    @Override
    public int delete(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "status", "modifyAt", "userId");
        return this.subMovieTypeDao.delete(jsonObject);
    }

    @Override
    public int count() {
        return this.subMovieTypeDao.count() + 1;
    }
}
