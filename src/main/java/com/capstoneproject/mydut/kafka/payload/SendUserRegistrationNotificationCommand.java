package com.capstoneproject.mydut.kafka.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author vndat00
 * @since 10/20/2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(builderMethodName = "newBuilder", setterPrefix = "set")
public class SendUserRegistrationNotificationCommand extends SendMailPayload implements Serializable {
    private String username;
    private String token;
    private String url;
    private String fullName;
}
