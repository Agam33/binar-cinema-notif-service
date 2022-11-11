package com.ra.notification.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.ra.notification.dto.request.RequestFCM;

@Service
public class FCMServiceImpl implements FCMService {

    @Override
    public void topicNotification(RequestFCM requestFCM) {
        Message message = Message.builder()
                .setNotification(getNotification(requestFCM.getTitle(),
                        requestFCM.getMessage()))
                .setAndroidConfig(getAndroidConfig(requestFCM.getTopic()))
                .setTopic(requestFCM.getTopic())
                .build();

        FirebaseMessaging.getInstance().sendAsync(message);

    }

    @Override
    public void tokenNotification(RequestFCM requestFCM) {
        Message message = Message.builder()
                .setNotification(getNotification(requestFCM.getTitle(),
                        requestFCM.getMessage()))
                .setAndroidConfig(getAndroidConfig(requestFCM.getTopic()))
                .setToken(requestFCM.getToken())
                .build();

        FirebaseMessaging.getInstance().sendAsync(message);

    }

    private Notification getNotification(String title,
            String body) {
        return Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMillis(2).toMillis())
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setTag(topic)
                        .build())
                .build();
    }

}
