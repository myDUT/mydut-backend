package com.capstoneproject.mydut.service.impl;

import com.capstoneproject.mydut.kafka.payload.SendMailPayload;
import com.capstoneproject.mydut.kafka.payload.SendUserRegistrationNotificationCommand;
import com.capstoneproject.mydut.service.UserEmailKafkaService;
import com.capstoneproject.mydut.util.FileUtils;
import com.capstoneproject.mydut.util.MailUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author vndat00
 * @since 10/20/2024
 */

@Service
@RequiredArgsConstructor
public class UserEmailKafkaServiceImpl implements UserEmailKafkaService {
    private final MailUtils mailUtils;

    @Override
    public void sendEmailForUserRegistrationNotification(SendUserRegistrationNotificationCommand command) {
        try {
            var template = FileUtils.readMailTemplate("user-registration-mail-template.txt");
            var subject = "[myDUT] Welcome new account";

            mailUtils.sendEmailWithRetry(subject, template, command);
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }

    }

    public <T extends SendMailPayload> String mergeContent(T payload, String content) {
        return StringUtils.EMPTY;
    }

}
