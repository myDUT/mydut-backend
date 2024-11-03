package com.capstoneproject.mydut.util;

import com.capstoneproject.mydut.common.EmailProperties;
import com.capstoneproject.mydut.common.SimpleRetryStrategy;
import com.capstoneproject.mydut.kafka.payload.SendMailPayload;
import com.capstoneproject.mydut.mailclient.MailClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author vndat00
 * @since 11/3/2024
 */

@Component
@RequiredArgsConstructor
@Log4j2
public class MailUtils {
    private static final String NO_REPLY_EMAIL = "no-reply@mydut.com";
    private final MailClient mailClient;
    private final EmailProperties emailProperties;

    public void sendEmailWithRetry(String subject, String content, SendMailPayload payload) {
        SimpleRetryStrategy retryStrategy = new SimpleRetryStrategy(3, 4000L);
        AtomicInteger retryCount = new AtomicInteger();
        AtomicReference<String> exceptionMessage = new AtomicReference<>();
        AtomicBoolean failed = new AtomicBoolean(true);

        try {
            retryStrategy.execute(
                    retries -> {
                        retryCount.set(retries);
                        mailClient.sendEmail(emailProperties.getSmtpUsername(), subject, content, payload.getTo(), payload.getCc(), payload.getBcc());
                        failed.set(false);
                    },
                    exception -> exceptionMessage.set(exception.getMessage())
            );
        } catch (Exception e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }

        if (retryCount.get() > 0) {
            log.info("Send mail to recipient {} {} with {} retries", payload.getTo(), failed.get() ? "successfully" : "unsuccessfully", retryCount.get());
            log.info("Detail exception message: {}.", exceptionMessage.get());
        }
    }
}
