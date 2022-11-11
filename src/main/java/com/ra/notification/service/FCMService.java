package com.ra.notification.service;

import com.ra.notification.dto.request.RequestFCM;

public interface FCMService {
    void topicNotification(RequestFCM requestFCM);

    void tokenNotification(RequestFCM requestFCM);
}
