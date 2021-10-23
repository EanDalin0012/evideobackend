package com.evideo.evideobackend.core.service.implement;

import com.evideo.evideobackend.core.dao.RoleDao;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.RoleService;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImplement implements RoleService {
    final RoleDao roleDao;
    RoleServiceImplement(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    @Override
    public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "status");
        return this.roleDao.read(jsonObject);
    }
}
