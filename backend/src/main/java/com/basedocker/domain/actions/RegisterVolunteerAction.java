package com.basedocker.domain.actions;

import com.basedocker.application.dto.RegisterVolunteerRequestDto;
import com.basedocker.domain.service.VolunteerService;
import com.basedocker.domain.valueobjects.EmailConfirmation;
import com.basedocker.domain.valueobjects.EmailTemplate;
import com.basedocker.domain.valueobjects.PlainPassword;
import com.basedocker.infrastructure.EmailService;
import com.basedocker.domain.Email;
import com.basedocker.infrastructure.TemplateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RegisterVolunteerAction {

    @Value("${basedocker.api.v1.confirmation-email}")
    private String emailConfirmationBaseUrl;

    private final VolunteerService volunteerService;

    private final EmailService emailService;

    private final TemplateService templateService;

    public RegisterVolunteerAction(VolunteerService volunteerService, EmailService emailService, TemplateService templateService) {
        this.volunteerService = volunteerService;
        this.emailService = emailService;
        this.templateService = templateService;
    }

    public void execute(RegisterVolunteerRequestDto dto) {
        EmailConfirmation emailConfirmation = EmailConfirmation.from(dto.getEmail(), emailConfirmationBaseUrl);
        volunteerService.registerVolunteer(PlainPassword.from(dto.getPassword()), emailConfirmation);
        EmailTemplate emailTemplate = templateService.getEmailConfirmationTemplate(emailConfirmation);
        Email email = Email.createFrom(emailConfirmation, emailTemplate);
        emailService.sendEmail(email);
    }
}