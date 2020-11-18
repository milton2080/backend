package com.basedocker.application.controller;

import com.basedocker.application.dto.RegisterVolunteerRequestDto;
import com.basedocker.domain.actions.RegisterVolunteerAction;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/volunteers")
public class VolunteerApiController {

    private final RegisterVolunteerAction registerVolunteerAction;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerVolunteer(@Validated @RequestBody RegisterVolunteerRequestDto dto) {
        registerVolunteerAction.execute(dto);
    }
}