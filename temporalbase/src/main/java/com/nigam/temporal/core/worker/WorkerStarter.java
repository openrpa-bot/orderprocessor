package com.nigam.temporal.core.worker;

import com.nigam.temporal.core.HelloActivityImpl;
import com.nigam.temporal.core.HelloWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WorkerStarter implements CommandLineRunner {

    private final WorkerFactory workerFactory;

    public WorkerStarter(WorkerFactory workerFactory) {
        this.workerFactory = workerFactory;
    }

    @Override
    public void run(String... args) {
        // Define the task queue name
        String taskQueue = "HelloSampleTaskQueue";

        // Create a Worker that listens on the task queue
        Worker worker = workerFactory.newWorker(taskQueue);

        // Register the Workflow implementation with the Worker
        worker.registerWorkflowImplementationTypes(HelloWorkflowImpl.class);

        // Register the Activity implementation with the Worker
        worker.registerActivitiesImplementations(new HelloActivityImpl());

        // Start all the Workers created by this factory
        workerFactory.start();
    }
}