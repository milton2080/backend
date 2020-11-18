package com.basedocker.unit;

import com.basedocker.domain.ExpressRegistrationVolunteer;
import com.basedocker.domain.Roles;
import com.basedocker.domain.exception.RoleNotFoundException;
import com.basedocker.domain.repository.VolunteerRepository;
import com.basedocker.domain.valueobjects.EmailConfirmation;
import com.basedocker.domain.valueobjects.PasswordHash;
import com.basedocker.infrastructure.orm.repository.JpaEmailConfirmationRepository;
import com.basedocker.infrastructure.orm.repository.JpaRoleRepository;
import com.basedocker.infrastructure.orm.repository.JpaVolunteerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VolunteerRepositoryTest {
    @Mock
    private JpaVolunteerRepository jpaVolunteerRepository;
    @Mock
    private JpaEmailConfirmationRepository jpaEmailConfirmationRepository;
    @Mock
    private JpaRoleRepository jpaRoleRepository;

    @Test
    void saving_volunteer_should_throw_role_not_found_if_role_doesnt_exist() {
        // GIVEN
        EmailConfirmation emailConfirmation = EmailConfirmation.from("foo@basedocker.com", "");
        PasswordHash passwordHash = new PasswordHash("123456");
        ExpressRegistrationVolunteer expressRegistrationVolunteer = new ExpressRegistrationVolunteer(passwordHash, emailConfirmation);
        when(jpaRoleRepository.findByName(Roles.VOLUNTEER.toString())).thenReturn(Optional.empty());

        // WHEN + THEN
        VolunteerRepository volunteerRepository = new VolunteerRepository(jpaVolunteerRepository, jpaEmailConfirmationRepository, jpaRoleRepository);
        assertThrows(RoleNotFoundException.class, () -> volunteerRepository.save(expressRegistrationVolunteer));
    }

}
