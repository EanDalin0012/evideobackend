package com.evideo.evideobackend.core.events;

import com.evideo.evideobackend.core.dto.JsonObject;
import org.springframework.context.ApplicationEvent;

public class HistoryUserLoginEvent extends ApplicationEvent {
//    public HistoryUserLoginEvent(Object source, JsonObject object) {
//        super(source);
//    }

    public HistoryUserLoginEvent(JsonObject deviceInfo) {
        super(deviceInfo);
    }
}
