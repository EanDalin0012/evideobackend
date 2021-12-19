package com.evideo.evideobackend.adminlte.service.impl;

import com.evideo.evideobackend.adminlte.dao.ClientSettingDAO;
import com.evideo.evideobackend.adminlte.service.ClientSettingService;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.dto.JsonObjectArray;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class ClientSettingServiceImpl implements ClientSettingService {
    final ClientSettingDAO clientSettingDAO;

    ClientSettingServiceImpl(ClientSettingDAO clientSettingDAO) {
        this.clientSettingDAO = clientSettingDAO;
    }
    @Override
    public JsonObjectArray read() {
        return this.clientSettingDAO.read();
    }

    @Override
    public int insertVideoTypeDt(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject,  "id","videoTypeId", "videoSubTypeId");
        return this.clientSettingDAO.insertVideoTypeDt(jsonObject);
    }

    @Override
    public int deleteVideoTypeDt(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject,  "videoTypeId", "videoSubTypeId");
        return this.clientSettingDAO.deleteVideoTypeDt(jsonObject);
    }

    @Override
    public int count() {
        return this.clientSettingDAO.count() + 1;
    }
}
