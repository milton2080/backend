package com.basedocker.infrastructure;

import com.basedocker.domain.Email;

public interface EmailService {

    boolean sendEmail (Email email);
}
