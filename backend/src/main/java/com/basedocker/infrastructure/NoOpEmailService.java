package com.basedocker.infrastructure;

import com.basedocker.domain.Email;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "basedocker.feature.email.enabled", havingValue = "false")
public class NoOpEmailService implements EmailService {
    @Override
    public boolean sendEmail(Email email) {
        return true;
    }
}
