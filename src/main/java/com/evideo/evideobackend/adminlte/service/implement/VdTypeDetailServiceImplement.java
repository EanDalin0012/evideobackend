package com.evideo.evideobackend.adminlte.service.implement;

import org.springframework.stereotype.Service;

import com.evideo.evideobackend.adminlte.dao.VdTypeDetailDao;
import com.evideo.evideobackend.adminlte.service.VdTypeDetailService;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;

@Service
public class VdTypeDetailServiceImplement implements VdTypeDetailService {
	
	final VdTypeDetailDao vdTypeDetailDao;
	VdTypeDetailServiceImplement(VdTypeDetailDao vdTypeDetailDao) {
		this.vdTypeDetailDao = vdTypeDetailDao;
	}
	
	@Override
	public int create(JsonObject jsonObject) throws ValidatorException {
		ValidatorUtil.validate(jsonObject,  "vdTypeId", "vdSubTypeId");
		return this.vdTypeDetailDao.create(jsonObject);
	}

	@Override
	public JsonObjectArray read() {
		return this.vdTypeDetailDao.read();
	}

	@Override
	public int update(JsonObject jsonObject) throws ValidatorException {
		ValidatorUtil.validate(jsonObject, "id", "vdTypeId", "vdSubTypeId");
		return this.vdTypeDetailDao.update(jsonObject);
	}

	@Override
	public int delete(JsonObject jsonObject) throws ValidatorException {
		ValidatorUtil.validate(jsonObject, "id");
		return this.vdTypeDetailDao.delete(jsonObject);
	}

	@Override
	public int count() {
		return this.vdTypeDetailDao.count() + 1;
	}

	@Override
	public JsonObject readObj(JsonObject jsonObject) throws ValidatorException {
		ValidatorUtil.validate(jsonObject, "vdSubTypeId");
		return this.vdTypeDetailDao.readObj(jsonObject);
	}

	@Override
	public JsonObjectArray readVdSubType(JsonObject jsonObject) throws ValidatorException {
		ValidatorUtil.validate(jsonObject, "vdTypeId");
		return this.vdTypeDetailDao.readVdSubType(jsonObject);
	}
	
}
