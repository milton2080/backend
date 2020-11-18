package com.basedocker.domain.repository;

import com.basedocker.domain.*;
import com.basedocker.domain.exception.RoleNotFoundException;
import com.basedocker.infrastructure.orm.repository.JpaEmailConfirmationRepository;
import com.basedocker.infrastructure.orm.repository.JpaRoleRepository;
import com.basedocker.infrastructure.orm.repository.JpaVolunteerRepository;
import com.basedocker.infrastructure.orm.model.Credential;
import com.basedocker.infrastructure.orm.model.EmailConfirmation;
import com.basedocker.infrastructure.orm.model.Role;
import com.basedocker.infrastructure.orm.model.Volunteer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
@Transactional
@AllArgsConstructor
public class VolunteerRepository {

    @Autowired
    private final JpaVolunteerRepository jpaVolunteerRepository;

    @Autowired
    private final JpaEmailConfirmationRepository jpaEmailConfirmationRepository;

    @Autowired
    private final JpaRoleRepository jpaRoleRepository;

    public Integer save(ExpressRegistrationVolunteer expressVolunteer) {
        Role role = jpaRoleRepository.findByName(Roles.VOLUNTEER.toString())
                .orElseThrow(() -> new RoleNotFoundException("Role VOLUNTEER not found."));
        EmailConfirmation emailConfirmation = EmailConfirmation.builder()
                .email(expressVolunteer.getEmail())
                .hash(expressVolunteer.getConfirmationToken())
                .build();
        emailConfirmation = jpaEmailConfirmationRepository.save(emailConfirmation);
        Credential credential = Credential.builder()
                .email(expressVolunteer.getEmail())
                .hashedPassword(expressVolunteer.getHashedPassword())
                .roles(Collections.singleton(role))
                .emailConfirmed(false)
                .emailConfirmation(emailConfirmation)
                .build();
        Volunteer volunteer = Volunteer.builder()
                .credential(credential)
                .build();
        return jpaVolunteerRepository.save(volunteer).getId();
    }
}
