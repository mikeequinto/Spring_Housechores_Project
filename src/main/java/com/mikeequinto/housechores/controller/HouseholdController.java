package com.mikeequinto.housechores.controller;

import com.mikeequinto.housechores.models.Household;
import com.mikeequinto.housechores.payload.request.HouseholdRequest;
import com.mikeequinto.housechores.payload.response.MessageResponse;
import com.mikeequinto.housechores.services.HouseholdService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/household")
@AllArgsConstructor
public class HouseholdController {

    private final HouseholdService householdService;

    @PostMapping(path = "")
    public ResponseEntity<MessageResponse> addHousehold(@RequestBody HouseholdRequest request) {
        try {
            Household household =  householdService.createHousehold(request);
            return new ResponseEntity(
                    household,
                    HttpStatus.CREATED
            );
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(
                    new MessageResponse(e.getMessage()),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }
}
