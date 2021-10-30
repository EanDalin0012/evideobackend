package com.evideo.evideobackend.core.batch.task;

import com.evideo.evideobackend.core.rest.ClientCloseApplicationRest;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class VdSourceTask implements Tasklet {
    static Logger log = Logger.getLogger(ClientCloseApplicationRest.class.getName());

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception
    {
        log.info("VdSourceTask start..");
        // ... your code
        log.info("VdSourceTask done..");
        return RepeatStatus.FINISHED;
    }
}
