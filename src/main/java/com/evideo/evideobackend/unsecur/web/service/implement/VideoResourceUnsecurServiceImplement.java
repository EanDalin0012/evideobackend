package com.evideo.evideobackend.unsecur.web.service.implement;

import org.springframework.stereotype.Service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import com.evideo.evideobackend.unsecur.web.dao.VideoResourceUnsecurDao;
import com.evideo.evideobackend.unsecur.web.service.VideoResourceUnsecurService;

@Service
public class VideoResourceUnsecurServiceImplement implements VideoResourceUnsecurService{

	final VideoResourceUnsecurDao videoResourceUnsecurDao;
	VideoResourceUnsecurServiceImplement(VideoResourceUnsecurDao videoResourceUnsecurDao) {
		this.videoResourceUnsecurDao = videoResourceUnsecurDao;
	}
	
	@Override
	public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
		ValidatorUtil.validate(jsonObject,  "status", "vdId");
		return this.videoResourceUnsecurDao.read(jsonObject);
	}

}
