package com.basedocker.unit;

import com.basedocker.domain.exception.EmailNotValidException;
import com.basedocker.domain.valueobjects.EmailConfirmation;
import com.basedocker.domain.valueobjects.EmailTemplate;
import com.basedocker.infrastructure.TemplateService;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTemplateShould {


    private final String baseUrl = "https://plataforma.basedocker.com/api/v1/email-confirmation/";

    @Test
    void receive_a_template_and_parse_it() {
        // GIVEN
        TemplateService templateService = new TemplateService();
        EmailConfirmation emailConfirmation = EmailConfirmation.from("foo@fuu.com", baseUrl);
        EmailTemplate emailTemplate = templateService.getEmailConfirmationTemplate(emailConfirmation);
        Map<String, String> variables = new HashMap<>();
        variables.put("CONFIRMATION_URL", emailConfirmation.getUrl() );

        // WHEN
        EmailTemplate result = emailTemplate.parse(variables);

        // THEN
        assertThat(result.getParsedTemplate().contains(emailConfirmation.getUrl()), is(true));
    }

    @Test
    void fail_when_cannot_replace_all_variables() {
        // GIVEN
        TemplateService templateService = new TemplateService();
        EmailConfirmation emailConfirmation = EmailConfirmation.from("foo@basedocker.com", baseUrl);
        EmailTemplate emailTemplate = templateService.getEmailConfirmationTemplate(emailConfirmation);
        Map<String, String> variables = new HashMap<>();

        // WHEN THEN
        assertThrows(EmailNotValidException.class, () -> emailTemplate.parse(variables));
    }


}