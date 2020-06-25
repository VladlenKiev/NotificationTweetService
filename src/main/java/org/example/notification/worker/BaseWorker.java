package org.example.notification.worker;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseWorker {

    private ExternalTaskClient externalTaskClient;
    private final String topic;

    private Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

    public BaseWorker(ExternalTaskClient externalTaskClient, String topic) {
        // bootstrap the client
        this.externalTaskClient = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(20000)
                .lockDuration(10000)
                .maxTasks(1)
                .build();
        this.topic = topic;
    }

    public BaseWorker(String topic) {
        this.topic = topic;
    }

    public void startWorker(){
        LOGGER.info("Worker has started!");

        // subscribe to the topic
        TopicSubscriptionBuilder subscriptionBuilder = externalTaskClient
                .subscribe("notificationTweet");
    }

    protected abstract void handleTask(ExternalTask externalTask, ExternalTaskService externalTaskService);
}
