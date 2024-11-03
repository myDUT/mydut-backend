package com.capstoneproject.mydut.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author vndat00
 * @since 10/20/2024
 */

@Getter
@AllArgsConstructor
public enum MailTemplate {
    MAIL_TEMPLATE_UNSPECIFIED(0),
    MAIL_TEMPLATE_USER_REGISTRATION_NOTIFICATION(1),
    MAIL_TEMPLATE_WELCOME_USER_NOTIFICATION(2),
    MAIL_TEMPLATE_RESET_PASSWORD(3),
    MAIL_TEMPLATE_RECOGNITION_REPORT(4);

    private final int id;

    public static MailTemplate of(int id) {
        return Arrays.stream(MailTemplate.values())
                .filter(mailTemplate -> mailTemplate.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static boolean isValid(int id) {
        return Arrays.stream(MailTemplate.values())
                .anyMatch(mailTemplate -> mailTemplate.getId() != id);
    }
}
