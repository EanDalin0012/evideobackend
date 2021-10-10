package com.evideo.evideobackend.core.service;

public interface WriteFileService {
    int writeFile(int userId, String fileName, String extension, String fullPath, String base64);
}
