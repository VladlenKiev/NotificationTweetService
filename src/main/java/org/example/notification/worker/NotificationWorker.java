package org.example.notification.worker;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;

public class NotificationWorker extends BaseWorker{
    private Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

    public NotificationWorker(ExternalTaskClient externalTaskClient, String topic) {
        super(externalTaskClient, topic);
    }

    public NotificationWorker(String topic) {
        super(topic);
    }

    @Override
    protected void handleTask(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        Boolean doFail = externalTask.getVariable("doFail");
        Integer retries = externalTask.getRetries();
        if (retries ==null || retries == 0)
            retries = 3;

        try {
            if (doFail)
                throw new RuntimeException("Hey man, mth is wrong...");

            String content = externalTask.getVariable("content");
            LOGGER.info("Sorry, your tweet has been rejected RuntimeException {}", content);

            HashMap<String, Object> variables = new HashMap<>();
            variables.put("notificationTimestamp", LocalDateTime.now().toString());
            externalTaskService.complete(externalTask, variables);

        } catch (Exception e){
            retries-= 1;
            externalTaskService.handleFailure(externalTask, e.getMessage(), Arrays.toString(e.getStackTrace()),retries, 1000);
        }


        // handle job
        /*subscriptionBuilder.handler((externalTask, externalTaskService) -> {
            String content = externalTask.getVariable("content");
            System.out.println("Sorry, your tweet has been rejected: " + content);
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("notficationTimestamp", new Date());
            externalTaskService.complete(externalTask, variables);
        });

        subscriptionBuilder.open();*/
    }
}
