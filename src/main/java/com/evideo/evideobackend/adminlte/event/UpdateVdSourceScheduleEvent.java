package com.evideo.evideobackend.adminlte.event;

import org.springframework.context.ApplicationEvent;

import com.evideo.evideobackend.core.dto.JsonObject;

public class UpdateVdSourceScheduleEvent extends ApplicationEvent {
	public UpdateVdSourceScheduleEvent(JsonObject jsonObject) {
        super(jsonObject);
    }
}
