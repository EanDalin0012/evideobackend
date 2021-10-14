package com.evideo.evideobackend.core.service.implement;

import com.evideo.evideobackend.core.dao.FileDao;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.FileService;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImplement implements FileService {

    final FileDao fileDao;

    FileServiceImplement(FileDao fileDao) {
        this.fileDao = fileDao;
    }
    @Override
    public int create(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "fileName", "fileExtension","fileSource", "status", "createAt", "userId");
        return this.fileDao.create(jsonObject);
    }

    @Override
    public JsonObject readBySourceId(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id");
        return this.fileDao.read(jsonObject);
    }

    @Override
    public int count() {
        return this.fileDao.count() + 1;
    }

    @Override
    public int delete(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id");
        return this.fileDao.delete(jsonObject);
    }
}
