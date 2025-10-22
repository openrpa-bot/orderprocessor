package com.nigam.quartz.callback;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@DisallowConcurrentExecution
public final class ClearingHistoryJob extends QuartzJobBean {

    @Autowired
    private Config clearingHistoryConfig;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("executeInternal data Cleanup job Started");
        //jmsTemplate.convertAndSend("Job1", "Rahul");
        log.info("executeInternal data Cleanup job End");
    }

    @Setter
    @Getter
    public static class Config {

        private int daysToExpiry;
    }
}
