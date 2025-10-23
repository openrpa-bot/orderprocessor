package com.nigam.dbcrawler.quartz.callback;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@DisallowConcurrentExecution
public final class AfterMarketCrawler extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("executeInternal data download job Started");
        //jmsTemplate.convertAndSend("Job1", "Rahul");
        log.info("executeInternal data download job End");
    }
}
