package com.capstoneproject.mydut.common;

import lombok.Getter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author vndat00
 * @since 10/23/2024
 */

@Component
@Getter
public class EmailProperties {
    private final String defaultEmailCharset = "UTF-8";
    private final String smtpHost;
    private final Integer smtpPort;
    private final String smtpUsername;
    private final String smtpPassword;
    private final Boolean smtpAuth;
    private final Boolean smtpSSL;
    private final Boolean smtpStartTLS;
    private final Boolean useSES;

    public EmailProperties(Environment env) {
        this.smtpHost = env.getRequiredProperty("smtp.mail.host");
        this.smtpPort = env.getRequiredProperty("smtp.mail.port", Integer.class);
        this.smtpUsername = env.getRequiredProperty("smtp.mail.username");
        this.smtpPassword = env.getRequiredProperty("smtp.mail.password");
        this.smtpAuth = env.getProperty("smtp.mail.auth", Boolean.class, Boolean.FALSE);
        this.smtpSSL = env.getProperty("smtp.mail.ssl", Boolean.class, Boolean.FALSE);
        this.smtpStartTLS = env.getProperty("smtp.mail.starttls", Boolean.class, Boolean.FALSE);
        this.useSES = env.getProperty("smtp.use.ses", Boolean.class, Boolean.FALSE);
    }
}
