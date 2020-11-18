package com.basedocker.unit;

import com.basedocker.domain.Email;
import com.basedocker.domain.exception.EmailNotValidException;
import com.basedocker.domain.valueobjects.EmailConfirmation;
import com.basedocker.domain.valueobjects.EmailTemplate;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

class EmailShould {

    @Test
    void not_allow_empty_fields() {
        //GIVEN
        EmailConfirmation emailConfirmation = EmailConfirmation.from("", "");
        EmailTemplate emailTemplate = new EmailTemplate("template-body");
        //WHEN +THEN
        assertThrows(EmailNotValidException.class, () -> Email.createFrom(emailConfirmation, emailTemplate));
    }

    @Test
    void create_a_valid_email_object() {
        //GIVEN
        EmailConfirmation emailConfirmation = EmailConfirmation.from("foo@basedocker.com", "");
        EmailTemplate emailTemplate = new EmailTemplate("template-body");
        //WHEN
        Email email = Email.createFrom(emailConfirmation, emailTemplate);
        //THEN
        assertThat(email.getFrom(), is("noreply@basedocker.com"));
        assertThat(email.getTo(), is(emailConfirmation.getEmailAddress()));
        assertThat(email.getSubject(), is("Confirmación de la cuenta en basedocker"));
        assertThat(email.getBody(), is(emailTemplate.getParsedTemplate()));
    }

}