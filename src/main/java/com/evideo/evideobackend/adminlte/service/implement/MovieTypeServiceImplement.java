package com.evideo.evideobackend.adminlte.service.implement;

import com.evideo.evideobackend.adminlte.dao.MovieTypeDao;
import com.evideo.evideobackend.adminlte.service.CRUDService;
import com.evideo.evideobackend.adminlte.service.MovieTypeService;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MovieTypeServiceImplement implements CRUDService, MovieTypeService {
    static Logger log = Logger.getLogger(MovieTypeServiceImplement.class.getName());

    final MovieTypeDao movieTypeDao;
    MovieTypeServiceImplement(MovieTypeDao movieTypeDao) {
        this.movieTypeDao = movieTypeDao;
    }

    @Override
    public int create(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "vdType", "status", "createAt", "userId", "settingClient");
        return this.movieTypeDao.create(jsonObject);
    }

    @Override
    public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject,  "status");
        return this.movieTypeDao.read(jsonObject);
    }

    @Override
    public int update(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "vdType", "status", "modifyAt", "userId");
        return this.movieTypeDao.update(jsonObject);
    }

    @Override
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
