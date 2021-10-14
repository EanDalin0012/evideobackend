package com.evideo.evideobackend.adminlte.event;

import com.evideo.evideobackend.core.dto.JsonObject;
import org.springframework.context.ApplicationEvent;

public class RemoveFileEvent extends ApplicationEvent {
    public RemoveFileEvent(JsonObject jsonObject) {
        super(jsonObject);
    }
}
