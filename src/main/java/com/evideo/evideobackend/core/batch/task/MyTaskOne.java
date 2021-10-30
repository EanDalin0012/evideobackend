package com.evideo.evideobackend.core.batch.task;

import com.evideo.evideobackend.core.rest.ClientCloseApplicationRest;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyTaskOne implements Tasklet {
    static Logger log = Logger.getLogger(ClientCloseApplicationRest.class.getName());

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception
    {
        log.info("MyTaskOne start..");
        // ... your code
        log.info("MyTaskOne done..");
        return RepeatStatus.FINISHED;
    }
}
