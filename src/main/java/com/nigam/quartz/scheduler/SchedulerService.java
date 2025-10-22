package com.nigam.quartz.scheduler;

import com.nigam.quartz.callback.ClearingHistoryJob;
import com.nigam.quartz.callback.DataDownloadJob;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.NameMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SchedulerService implements JobListener {

    private static final String JOB_NAME = "MY_JOB";
    private static final String SCHEDULER_SERVICE_GROUP = "SCHEDULER_SERVICE";
    private static final String JOB_CLEARING_HISTORY_JOB_NAME = "CLEARING_JOB";
    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    private void init() throws SchedulerException{
       scheduler.getListenerManager().addJobListener(this, NameMatcher.jobNameEquals(JOB_NAME));
       JobDetail jobDetail = JobBuilder.newJob(DataDownloadJob.class)
                //This will allow other node to pick up the job if executing node fails.
                // It will not cause job to be re-executed when exception occurs
                // ref: https://stackoverflow.com/questions/19267263/quartz-jobdetail-requestrecovery
                // ref: http://www.quartz-scheduler.org/documentation/quartz-2.2.2/configuration/ConfigJDBCJobStoreClustering.html
                .requestRecovery()
                .storeDurably()
                .withIdentity(JOB_NAME, SCHEDULER_SERVICE_GROUP)
                .build();
       tryToAddJob(jobDetail);

        JobDetail clearingHistoryJobDetail = JobBuilder.newJob(ClearingHistoryJob.class)
                .storeDurably()
                .withIdentity(JOB_CLEARING_HISTORY_JOB_NAME, SCHEDULER_SERVICE_GROUP)
                .build();
        tryToAddJob(clearingHistoryJobDetail);

        Trigger clearingHistoryJobTrigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail.getKey())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().
                        withIntervalInSeconds(60).
                        repeatForever())
                .withIdentity(JOB_CLEARING_HISTORY_JOB_NAME, SCHEDULER_SERVICE_GROUP)
                .build();
        tryToAddTrigger(clearingHistoryJobTrigger);

    }

    private void tryToAddJob(JobDetail jobDetail) throws SchedulerException{
        try {
            scheduler.addJob(jobDetail, false);
        }catch(ObjectAlreadyExistsException ex){
            log.warn("Job has not bean added to scheduler as " +
                    "it already exists. Job key: "+jobDetail.getKey());
        }
    }

    private void tryToAddTrigger(Trigger trigger) throws SchedulerException {
        try {
            scheduler.scheduleJob(trigger);
        } catch (ObjectAlreadyExistsException ex) {
            log.warn("Trigger has not bean added to scheduler as " +
                    "it already exists. Trigger key: "+trigger.getKey());
        }
    }
    //<editor-fold desc="JobListener: Listen for jobs being executed on this node and update job history accordingly.
    // NOTE: This is done in separate transaction and in separate DB, so history might break ACID rules.">
    @Override
    public String getName() {
        return SCHEDULER_SERVICE_GROUP;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
       log.warn("Unexpected job to be executed: " + context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.warn("Unexpected job vetoed: " + context.getJobDetail().getKey().getName());
    }
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.warn("Unexpected job Was executed: " + context.getJobDetail().getKey().getName());
    }

    private TriggerKey findOriginalTriggerKey(JobExecutionContext context){
        return context.isRecovering() ?
                context.getRecoveringTriggerKey() : context.getTrigger().getKey();
    }


     private Optional<String> checkJobAlreadyScheduledForExecution() throws SchedulerException{
        return scheduler.getTriggersOfJob(JobKey.jobKey(JOB_NAME, SCHEDULER_SERVICE_GROUP))
                .stream().filter(trigger -> Objects.nonNull(trigger.getNextFireTime()))
                .findFirst().map(Trigger::getKey).map(TriggerKey::getName);
    }

    String clearJobHistory() throws SchedulerException{
        return scheduleJobForNow(JOB_CLEARING_HISTORY_JOB_NAME, UUID.randomUUID().toString());
    }
    private String scheduleJobForNow(String jobName, String jobId) throws SchedulerException{
        Trigger nowTrigger = TriggerBuilder.newTrigger()
                .forJob(JobKey.jobKey(jobName, SCHEDULER_SERVICE_GROUP))
                .withIdentity(jobId, SCHEDULER_SERVICE_GROUP)
                .startNow()
                .build();
        scheduler.scheduleJob(nowTrigger);
        return nowTrigger.getKey().getName();
    }
}
