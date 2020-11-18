package com.basedocker.integration;

import com.basedocker.application.dto.RegisterVolunteerRequestDto;
import com.basedocker.domain.actions.RegisterVolunteerAction;
import com.basedocker.infrastructure.EmailService;
import com.basedocker.util.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestData.class)
class RegisterVolunteerActionShould {

    @Autowired
    private RegisterVolunteerAction registerVolunteerAction;

    @Autowired
    private TestData testData;

    @MockBean
    private EmailService emailService;



    @BeforeEach
    void beforeEach() {
        testData.resetData();
    }

    @Test
    void registering_a_volunteer_should_send_email_to_confirm_email_address(){
        //GIVEN
        RegisterVolunteerRequestDto dto = new RegisterVolunteerRequestDto("foo@basedocker.com", "plain-password");

        //WHEN
        registerVolunteerAction.execute(dto);

        //THEN
        verify(emailService, times(1)).sendEmail(any());
    }

}
