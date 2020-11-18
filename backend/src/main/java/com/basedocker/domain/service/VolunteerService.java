package com.basedocker.domain.service;

import com.basedocker.domain.*;
import com.basedocker.domain.repository.VolunteerRepository;
import com.basedocker.domain.valueobjects.EmailConfirmation;
import com.basedocker.domain.valueobjects.PlainPassword;
import com.basedocker.domain.valueobjects.PasswordHash;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VolunteerService {

    @Autowired
    private final VolunteerRepository volunteerRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public Integer registerVolunteer(PlainPassword plainPassword, EmailConfirmation emailConfirmation) {
        PasswordHash hash = new PasswordHash(passwordEncoder.encode(plainPassword.toString()));
        ExpressRegistrationVolunteer expressVolunteer = new ExpressRegistrationVolunteer(hash, emailConfirmation);
        return volunteerRepository.save(expressVolunteer);
    }
}
