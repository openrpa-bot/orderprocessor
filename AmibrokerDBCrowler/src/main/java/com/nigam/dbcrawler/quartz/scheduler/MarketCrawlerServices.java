package com.nigam.dbcrawler.quartz.scheduler;

import com.nigam.dbcrawler.quartz.callback.DuringMarketCrawler;
import com.nigam.dbcrawler.quartz.callback.AfterMarketCrawler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


//import javax.annotation.PostConstruct;

import static org.quartz.impl.matchers.KeyMatcher.keyEquals;

@Slf4j
@Service
public class MarketCrawlerServices implements JobListener {

    private static final String DURING_MARKET_JOB_NAME = "DURING_MARKET_DATA_CRAWLER_JOB";
    private static final String AFTER_MARKET_JOB_NAME = "AFTER_MARKET_DATA_CRAWLER_JOB";
    private static final String CRAWLER_JOB_GROUP = "CRAWLER_JOB";
    private static final String SCHEDULER_SERVICE_GROUP = "SCHEDULER_SERVICE_GROUP";

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    private void init() throws SchedulerException {

        // Add this class as a job listener
        scheduler.getListenerManager().addJobListener(this, keyEquals(JobKey.jobKey(DURING_MARKET_JOB_NAME, CRAWLER_JOB_GROUP)));

        // ========== DURING MARKET JOB ==========
        JobDetail duringMarketJob = JobBuilder.newJob(DuringMarketCrawler.class)
                .withIdentity(DURING_MARKET_JOB_NAME, CRAWLER_JOB_GROUP)
                .storeDurably()
                .requestRecovery()
                .build();

        tryToAddJob(duringMarketJob);

        Trigger duringMarketTrigger = TriggerBuilder.newTrigger()
                .forJob(duringMarketJob)
                .withIdentity("TRIGGER_DURING_MARKET", CRAWLER_JOB_GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 9-15 * * ?")
                        .inTimeZone(TimeZone.getTimeZone("Asia/Kolkata")))
                .build();


        tryToAddTrigger(duringMarketTrigger);

        // ========== AFTER MARKET JOB ==========
        JobDetail afterMarketJob = JobBuilder.newJob(AfterMarketCrawler.class)
                .withIdentity(AFTER_MARKET_JOB_NAME, CRAWLER_JOB_GROUP)
                .storeDurably()
                .build();

        tryToAddJob(afterMarketJob);

        Trigger afterMarketTrigger = TriggerBuilder.newTrigger()
                .forJob(afterMarketJob)
                .withIdentity("TRIGGER_AFTER_MARKET", CRAWLER_JOB_GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 16-22/2 * * ?")
                        .inTimeZone(TimeZone.getTimeZone("Asia/Kolkata")))
                .build();


        tryToAddTrigger(afterMarketTrigger);

        log.info("✅ SchedulerService initialized with 2 jobs: every minute + every hour");
    }

    // Helper to safely add jobs
    private void tryToAddJob(JobDetail jobDetail) throws SchedulerException {
        try {
            scheduler.addJob(jobDetail, false);
        } catch (ObjectAlreadyExistsException ex) {
            log.warn("Job already exists: {}", jobDetail.getKey());
        }
    }

    // Helper to safely add triggers
    private void tryToAddTrigger(Trigger trigger) throws SchedulerException {
        try {
            scheduler.scheduleJob(trigger);
        } catch (ObjectAlreadyExistsException ex) {
            log.warn("Trigger already exists: {}", trigger.getKey());
        }
    }

    // ==========================================================
    // JobListener implementation
    // ==========================================================

    @Override
    public String getName() {
        return SCHEDULER_SERVICE_GROUP;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("Job starting: {}", context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.warn("Job vetoed: {}", context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("Job finished: {}", context.getJobDetail().getKey().getName());
        if (jobException != null) {
            log.error("Job error: {}", jobException.getMessage(), jobException);
        }
    }

    // ==========================================================
    // Utility methods for manual scheduling or history clearing
    // ==========================================================

    private TriggerKey findOriginalTriggerKey(JobExecutionContext context) {
        return context.isRecovering() ?
                context.getRecoveringTriggerKey() :
                context.getTrigger().getKey();
    }

    private Optional<String> checkJobAlreadyScheduledForExecution(String jobName) throws SchedulerException {
        return scheduler.getTriggersOfJob(JobKey.jobKey(jobName, CRAWLER_JOB_GROUP))
                .stream()
                .filter(trigger -> Objects.nonNull(trigger.getNextFireTime()))
                .findFirst()
                .map(Trigger::getKey)
                .map(TriggerKey::getName);
    }

    public String triggerNow(String jobName) throws SchedulerException {
        Trigger nowTrigger = TriggerBuilder.newTrigger()
                .forJob(JobKey.jobKey(jobName, CRAWLER_JOB_GROUP))
                .withIdentity(UUID.randomUUID().toString(), SCHEDULER_SERVICE_GROUP)
                .startNow()
                .build();
        scheduler.scheduleJob(nowTrigger);
        return nowTrigger.getKey().getName();
    }
}
