package com.evideo.evideobackend.adminlte.service.impl;

import com.evideo.evideobackend.adminlte.dao.VideoDao;
import com.evideo.evideobackend.adminlte.service.VideoService;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {
    static Logger log = Logger.getLogger(VideoServiceImpl.class.getName());
    final VideoDao videoDao;

    VideoServiceImpl(VideoDao videoDao) {
        this.videoDao = videoDao;
    }

    @Override
    @PreAuthorize("hasAuthority('Movie_Create')")
    public int create(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "resourceId", "vdName","vdId","subVdTypeId", "status", "createAt", "userId");
        return this.videoDao.create(jsonObject);
    }

    @Override
    @PreAuthorize("hasAuthority('Movie_Read')")
    public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject,  "status");
        return this.videoDao.read(jsonObject);
    }

    @Override
    @PreAuthorize("hasAuthority('Movie_Update')")
    public int update(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "resourceId", "vdName","vdId","subVdTypeId", "status", "modifyAt", "userId");
        return this.videoDao.update(jsonObject);
    }

    @Override
    @PreAuthorize("hasAuthority('Movie_Delete')")
    public int delete(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "status", "modifyAt", "userId");
        return this.videoDao.delete(jsonObject);
    }

    @Override
    public int count() {
        return this.videoDao.count() + 1;
    }
}
