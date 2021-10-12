package com.evideo.evideobackend.adminlte.service.implement;

import com.evideo.evideobackend.adminlte.dao.VideoSourceLTEDao;
import com.evideo.evideobackend.adminlte.service.VideoSourceLTEService;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class VideoSourceLTEServiceImplement implements VideoSourceLTEService {

    final VideoSourceLTEDao videoSourceLTEDao;
    VideoSourceLTEServiceImplement(VideoSourceLTEDao videoSourceLTEDao) {
        this.videoSourceLTEDao = videoSourceLTEDao;
    }

    @Override
    public int create(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "scheduleYN", "isEnd", "resourceId", "vdId","part", "status", "createAt", "userId");
        return this.videoSourceLTEDao.create(jsonObject);
    }

    @Override
    public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject,  "status");
        return this.videoSourceLTEDao.read(jsonObject);
    }

    @Override
    public int update(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "resourceId", "vdName","vdId","subVdTypeId", "status", "modifyAt", "userId");
        return this.videoSourceLTEDao.update(jsonObject);
    }

    @Override
    public int delete(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "status", "modifyAt", "userId");
        return this.videoSourceLTEDao.delete(jsonObject);
    }

    @Override
    public int count() {
        return this.videoSourceLTEDao.count() + 1;
    }

    @Override
    public int inquiryPart(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject,  "vdId");
        return this.videoSourceLTEDao.inquiryPart(jsonObject) + 1;
    }
}