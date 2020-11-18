package com.basedocker.domain;

import com.basedocker.domain.exception.EmailNotValidException;
import com.basedocker.domain.valueobjects.EmailConfirmation;
import com.basedocker.domain.valueobjects.EmailTemplate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Email {
    private String subject;
    private String from;
    private String to;
    private String body;

    public static Email createFrom(EmailConfirmation emailConfirmation, EmailTemplate emailTemplate) {
        if (emailConfirmation.getEmailAddress().isEmpty()) {
            throw new EmailNotValidException("Error when build the email, the email address is empty");
        }

        return Email.builder()
                .from("noreply@basedocker.com")
                .to(emailConfirmation.getEmailAddress())
                .subject("Confirmación de la cuenta en basedocker")
                .body(emailTemplate.getParsedTemplate())
                .build();

    }

}
