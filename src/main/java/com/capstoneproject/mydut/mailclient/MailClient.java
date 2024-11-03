package com.capstoneproject.mydut.mailclient;

import java.util.List;

/**
 * @author vndat00
 * @since 10/22/2024
 */
public interface MailClient {
    boolean sendEmail(String from, String subject, String content, List<String> toAddresses, List<String> ccAddresses, List<String> bccAddresses);
}
