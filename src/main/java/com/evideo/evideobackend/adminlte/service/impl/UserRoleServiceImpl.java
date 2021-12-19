package com.evideo.evideobackend.adminlte.service.impl;

import com.evideo.evideobackend.adminlte.service.UserRoleService;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Override
    public int create(JsonObject jsonObject) throws ValidatorException {
        return 0;
    }

    @Override
    public JsonObjectArray read(JsonObject jsonObject) throws ValidatorException {
        return null;
    }

    @Override
    public int update(JsonObject jsonObject) throws ValidatorException {
        return 0;
    }

    @Override
    public int delete(JsonObject jsonObject) throws ValidatorException {
        return 0;
    }

    @Override
    public int count() {
        return 0;
    }
}
