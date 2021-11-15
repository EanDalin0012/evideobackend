package com.evideo.evideobackend.unsecur.web.service.implement;

import org.springframework.stereotype.Service;

import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import com.evideo.evideobackend.unsecur.web.dao.SubVideoTypeUnsecurDao;
import com.evideo.evideobackend.unsecur.web.service.SubVideoTypeUnsecurService;

@Service
public class SubVideoTypeUnsecurServiceImplement implements SubVideoTypeUnsecurService{

	final SubVideoTypeUnsecurDao subVideoTypeUnsecurDao;
	SubVideoTypeUnsecurServiceImplement(SubVideoTypeUnsecurDao subVideoTypeUnsecurDao) {
		this.subVideoTypeUnsecurDao = subVideoTypeUnsecurDao;
	}
	
	@Override
	public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
		ValidatorUtil.validate(jsonObject,  "status");
		return this.subVideoTypeUnsecurDao.read(jsonObject);
	}

}
