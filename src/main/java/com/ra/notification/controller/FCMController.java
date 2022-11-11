package com.ra.notification.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ra.notification.dto.request.RequestFCM;
import com.ra.notification.dto.response.Response;
import com.ra.notification.dto.response.ResponseError;
import com.ra.notification.service.FCMServiceImpl;
import com.ra.notification.util.Constants;

@RestController
@RequestMapping(Constants.NOTIFICATION_ENDPOINT)
public class FCMController {
    private final FCMServiceImpl fcmService;

    public FCMController(FCMServiceImpl fcmService) {
        this.fcmService = fcmService;
    }

    @PostMapping("/topic")
    public ResponseEntity<?> sendTopicNotification(@RequestBody RequestFCM requestFCM) {
        try {
            fcmService.topicNotification(requestFCM);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
                    new Date(), Constants.SUCCESS_MSG, null));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseError(null, new Date(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/token")
    public ResponseEntity<?> sendTokenNotification(@RequestBody RequestFCM requestFCM) {
        try {
            fcmService.tokenNotification(requestFCM);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(),
                    new Date(), Constants.SUCCESS_MSG, null));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseError(null, new Date(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
