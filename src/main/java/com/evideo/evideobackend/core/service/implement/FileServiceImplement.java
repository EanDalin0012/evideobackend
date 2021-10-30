package com.evideo.evideobackend.core.service.implement;

import com.evideo.evideobackend.core.dao.FileDao;
import com.evideo.evideobackend.core.dto.JsonObject;
import com.evideo.evideobackend.core.exception.ValidatorException;
import com.evideo.evideobackend.core.service.FileService;
import com.evideo.evideobackend.core.util.ValidatorUtil;
import org.springframework.stereotype.Service;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileServiceImplement implements FileService {

    @Value("${upload.path}")
    private String uploadPath;
    
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
    
    public void save(MultipartFile file) throws Exception {
        try {
            Path root = Paths.get("D:\\my-vd");
            Path resolve = root.resolve(file.getOriginalFilename());
            if (resolve.toFile()
                       .exists()) {
            	throw new Exception();
            }
            Files.copy(file.getInputStream(), resolve);
        } catch (Exception e) {
            throw e;
        }
    }
}
