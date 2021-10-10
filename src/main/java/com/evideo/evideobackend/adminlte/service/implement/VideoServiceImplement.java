package com.evideo.evideobackend.adminlte.service.implement;

import com.evideo.evideobackend.adminlte.dao.VideoDao;
import com.evideo.evideobackend.adminlte.service.VideoService;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImplement implements VideoService {
    static Logger log = Logger.getLogger(VideoServiceImplement.class.getName());
    final VideoDao videoDao;

    VideoServiceImplement(VideoDao videoDao) {
        this.videoDao = videoDao;
    }

    @Override
    public int create(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "resourceId", "vdName","vdId","subVdTypeId", "status", "createAt", "userId");
        return this.videoDao.create(jsonObject);
    }

    @Override
    public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject,  "status");
        return this.videoDao.read(jsonObject);
    }

    @Override
    public int update(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "resourceId", "vdName","vdId","subVdTypeId", "status", "modifyAt", "userId");
        return this.videoDao.update(jsonObject);
    }

    @Override
    public int delete(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "status", "modifyAt", "userId");
        return this.videoDao.delete(jsonObject);
    }

    @Override
    public int count() {
        return this.videoDao.count() + 1;
    }
}
