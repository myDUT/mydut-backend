package com.capstoneproject.mydut.mailclient.impl;

import com.capstoneproject.mydut.common.EmailProperties;
import com.capstoneproject.mydut.mailclient.MailClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * @author vndat00
 * @since 10/22/2024
 */

@Log4j2
public class SMTPClient implements MailClient {
    private final JavaMailSenderImpl mailSender;

    public SMTPClient(EmailProperties emailProperties) {
        this.mailSender = new JavaMailSenderImpl();

        mailSender.setHost(emailProperties.getSmtpHost());
        mailSender.setPort(emailProperties.getSmtpPort());
        mailSender.setUsername(emailProperties.getSmtpUsername());
        mailSender.setPassword(emailProperties.getSmtpPassword());

        var props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", emailProperties.getSmtpAuth().toString());
        props.put("mail.smtp.starttls.enable", emailProperties.getSmtpStartTLS().toString());
        props.put("mail.debug", "true");

    }

    @Override
    public boolean sendEmail(String from, String subject, String content, List<String> toAddresses, List<String> ccAddresses, List<String> bccAddresses) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setText(content, true);
            if (toAddresses != null && !toAddresses.isEmpty()) {
                helper.setTo(asListRecipient(toAddresses));
            }
            if (ccAddresses != null && !ccAddresses.isEmpty()) {
                helper.setCc(asListRecipient(ccAddresses));
            }
            if (bccAddresses != null && !bccAddresses.isEmpty()) {
                helper.setBcc(asListRecipient(bccAddresses));
            }
            helper.setSubject(subject);
            helper.setFrom(from);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Cannot send email from {} with subject: {}. Message {}", from, subject, e.getMessage());
            throw new RuntimeException(e);
        }
        return true;
    }

    private InternetAddress[] asListRecipient(List<String> addresses) throws AddressException {
        InternetAddress[] addrLst = new InternetAddress[addresses.size()];
        int index = 0;
        for (String address : addresses) {
            addrLst[index++] = new InternetAddress(address);
        }
        return addrLst;
    }
}
