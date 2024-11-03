package com.capstoneproject.mydut.service;

import com.capstoneproject.mydut.kafka.payload.SendUserRegistrationNotificationCommand;

/**
 * @author vndat00
 * @since 10/20/2024
 */
public interface UserEmailKafkaService {
    void sendEmailForUserRegistrationNotification(SendUserRegistrationNotificationCommand command);
}
