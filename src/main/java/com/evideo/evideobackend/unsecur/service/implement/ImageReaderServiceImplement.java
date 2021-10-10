package com.evideo.evideobackend.unsecur.service.implement;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.unsecur.dao.ImageReaderDao;
import com.evideo.evideobackend.unsecur.service.ImageReaderService;
import org.springframework.stereotype.Service;

@Service
public class ImageReaderServiceImplement implements ImageReaderService {
    final ImageReaderDao imageReaderDao;

    ImageReaderServiceImplement(ImageReaderDao imageReaderDao) {
        this.imageReaderDao = imageReaderDao;
    }
    @Override
    public JsonObject inquiryResourcesID(JsonObject param) {
        return this.imageReaderDao.inquiryResourcesID(param);
    }
}
