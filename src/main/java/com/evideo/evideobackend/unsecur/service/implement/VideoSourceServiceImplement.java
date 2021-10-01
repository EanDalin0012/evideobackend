package com.evideo.evideobackend.unsecur.service.implement;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.unsecur.dao.VideoSourceDao;
import com.evideo.evideobackend.unsecur.service.VideoSourceService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class VideoSourceServiceImplement implements VideoSourceService {
    static Logger log = Logger.getLogger(VideoSourceServiceImplement.class.getName());

    final VideoSourceDao videoSourceDao;
    VideoSourceServiceImplement(VideoSourceDao videoSourceDao) {
        this.videoSourceDao = videoSourceDao;
    }

    @Override
    public JsonObject inquiryVideoSource(int id) throws ValidatorException {
        log.info("vd id :"+id);
        if (id <= 0) {
            log.info("Invalid Video Source Id:"+id);
            throw new ValidatorException("invalidVDSourceId", "Invalid Video Source Id");
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.setInt("id", id);
        return this.videoSourceDao.inquiryVideoSource(jsonObject);
    }

    @Override
    public JsonObjectArray inquiryVideoSourceByVDId(int vdId) throws ValidatorException {
        log.info("vd id :"+vdId);
        if (vdId <= 0) {
            log.info("Invalid Video Id :"+vdId);
            throw new ValidatorException("invalidVDId", "Invalid Video ID");
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.setInt("vdId", vdId);
        return this.videoSourceDao.inquiryVideoSourceByVDId(jsonObject);
    }
}
