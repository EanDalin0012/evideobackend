package com.evideo.evideobackend.core.service.implement;

import com.evideo.evideobackend.core.dao.DeviceInfoDao;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.DeviceInfoInterface;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class DeviceInfoService implements DeviceInfoInterface {
    final DeviceInfoDao deviceInfoDao;

    DeviceInfoService(DeviceInfoDao deviceInfoDao) {
        this.deviceInfoDao = deviceInfoDao;
    }
    @Override
    public int save(JsonObject param) {
        return this.deviceInfoDao.save(param);
    }

    @Override
    public int updateDeviceInfo(JsonObject param) {
        return this.deviceInfoDao.updateDeviceInfo(param);
    }

    @Override
    public JsonObject inquiry(JsonObject param) {
        return this.deviceInfoDao.inquiry(param);
    }

    @Override
    public JsonObject inquiryByUserAgent(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "userName", "userAgent");
        return this.deviceInfoDao.inquiryByUserAgent(param);
    }

    @Override
    public int count() {
        return this.deviceInfoDao.count();
    }

    @Override
    public int deleteDeviceInfo(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "userName", "userAgent");
        return this.deviceInfoDao.deleteDeviceInfo(jsonObject);
    }
}
