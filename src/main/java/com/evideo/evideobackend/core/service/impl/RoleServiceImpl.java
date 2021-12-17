package com.evideo.evideobackend.core.service.impl;

import com.evideo.evideobackend.core.dao.RoleDao;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.RoleService;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    final RoleDao roleDao;
    RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    @Override
    public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "status");
        return this.roleDao.read(jsonObject);
    }
}
