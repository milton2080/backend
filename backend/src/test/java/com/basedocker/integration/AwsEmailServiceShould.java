package com.basedocker.integration;

import cloud.localstack.docker.LocalstackDockerExtension;
import cloud.localstack.docker.annotation.LocalstackDockerProperties;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.basedocker.domain.Email;
import com.basedocker.infrastructure.AwsEmailService;
import com.basedocker.util.AwsEnvVariablesExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ActiveProfiles("localstack")
@ExtendWith(AwsEnvVariablesExtension.class)
@ExtendWith(LocalstackDockerExtension.class)
@LocalstackDockerProperties(services = { "ses" })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, properties = {"basedocker.feature.email.enabled=true"})
class AwsEmailServiceShould {

    private final static String emailAddress = "test@basedocker.com";

    @Autowired
    private AwsEmailService awsEmailService;

    @Autowired
    private AmazonSimpleEmailService sesClient;

    @Test
    void send_an_email() {
        VerifyEmailIdentityRequest request = new VerifyEmailIdentityRequest().withEmailAddress(emailAddress);
        sesClient.verifyEmailIdentity(request);
        Email email = Email.builder()
                .from(emailAddress)
                .to(emailAddress)
                .subject("test subject")
                .body("test body")
                .build();

        boolean result = awsEmailService.sendEmail(email);

        assertThat(result, is(true));
    }
}
