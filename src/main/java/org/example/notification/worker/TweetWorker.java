package org.example.notification.worker;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TweetWorker extends BaseWorker{
    private Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

    public TweetWorker(ExternalTaskClient externalTaskClient, String topic) {
        super(externalTaskClient, topic);
    }

    public TweetWorker(String topic) {
        super(topic);
    }

    @Override
    protected void handleTask(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String content = externalTask.getVariable("content");
        LOGGER.info("Sorry, your tweet has been rejected {}", content);
        externalTaskService.complete(externalTask);
    }
}
