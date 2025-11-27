package com.nigam.brokercrawler.quartz.callback;

//import com.nigam.dbcrawler.service.MarketDBCrawler;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@DisallowConcurrentExecution
public final class DuringMarketBrokerCrawler extends QuartzJobBean {
    //@Autowired
    //MarketDBCrawler marketDBCrawler;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("executeInternal data Cleanup job Started");
        //jmsTemplate.convertAndSend("Job1", "Rahul");
        //marketDBCrawler.processAllRecords("ag_day");
        log.info("executeInternal data Cleanup job End");
    }
}
