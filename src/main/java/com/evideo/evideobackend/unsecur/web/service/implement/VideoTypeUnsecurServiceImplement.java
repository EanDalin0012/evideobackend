package com.evideo.evideobackend.unsecur.web.service.implement;

import org.springframework.stereotype.Service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import com.evideo.evideobackend.unsecur.web.dao.VideoTypeUnsecurDao;
import com.evideo.evideobackend.unsecur.web.service.VideoTypeUnsecurService;

@Service
public class VideoTypeUnsecurServiceImplement implements VideoTypeUnsecurService{
	
	final VideoTypeUnsecurDao videoTypeUnsecurDao;
	
	VideoTypeUnsecurServiceImplement(VideoTypeUnsecurDao videoTypeUnsecurDao) {
		this.videoTypeUnsecurDao = videoTypeUnsecurDao;
	}
	
	
	@Override
	public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
		ValidatorUtil.validate(jsonObject,  "status");
		return this.videoTypeUnsecurDao.read(jsonObject);
	}

}
