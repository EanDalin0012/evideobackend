package com.evideo.evideobackend.unsecur.web.service.implement;

import org.springframework.stereotype.Service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import com.evideo.evideobackend.unsecur.web.dao.VideoUnsecurDao;
import com.evideo.evideobackend.unsecur.web.service.VideoUnsecurService;

@Service
public class VideoUnsecurServiceImplement implements VideoUnsecurService{

	final VideoUnsecurDao videoUnsecurDao;
	VideoUnsecurServiceImplement(VideoUnsecurDao videoUnsecurDao) {
		this.videoUnsecurDao = videoUnsecurDao;
	}
	
	@Override
	public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
		ValidatorUtil.validate(jsonObject,  "status", "LIMIT");
		return this.videoUnsecurDao.read(jsonObject);
	}

}
