package com.capstoneproject.mydut.kafka.payload;

import com.capstoneproject.mydut.kafka.MailTemplate;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author vndat00
 * @since 10/19/2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(builderMethodName = "newBuilder", setterPrefix = "set")
public class SendMailPayload {
    private MailTemplate mailTemplate;
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
}
