package org.example.notification;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
import org.example.notification.worker.NotificationWorker;
import org.example.notification.worker.TweetWorker;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AppNotificator {
    public static void main(String[] args) {
        NotificationWorker notificationTweet = new NotificationWorker("notificationTweet");
//        TweetWorker twitterWorker = new TweetWorker("twitter");

        notificationTweet.startWorker();
//        twitterWorker.startWorker();
    }
}
