package com.capstoneproject.mydut.config;

import com.capstoneproject.mydut.common.EmailProperties;
import com.capstoneproject.mydut.mailclient.MailClient;
import com.capstoneproject.mydut.mailclient.impl.SMTPClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.parameters.P;

/**
 * @author vndat00
 * @since 11/3/2024
 */

@Configuration
@RequiredArgsConstructor
public class MailClientConfig {
    private final EmailProperties emailProperties;

    @Bean
    public MailClient mailClient() {
        // TODO: Implement with service AWS SES
        return new SMTPClient(emailProperties);
    }

}
