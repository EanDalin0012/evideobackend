package com.evideo.evideobackend.core.dao;

import com.evideo.evideobackend.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DefaultAuthenticationProviderDao {
    JsonObject getUserByName(JsonObject jsonNode);
    JsonObject authenticate(JsonObject jsonNode);
    int lockedUser(JsonObject jsonNode);
    int trackSaveUserLock(JsonObject jsonNode);
    int trackUpdateUserLock(JsonObject jsonNode);
    int trackUpdateUserIsLocked(JsonObject jsonNode);
    int updateLoginSuccess(JsonObject jsonNode);
    int deleteUserLockCountBYUserName(JsonObject jsonNode);
    JsonObject getTrackUserLockByUserName(JsonObject jsonNode);
    JsonObject getUserAccountLockByUserName(JsonObject jsonNode);
}
