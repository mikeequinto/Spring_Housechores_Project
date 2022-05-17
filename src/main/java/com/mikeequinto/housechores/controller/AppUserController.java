package com.mikeequinto.housechores.controller;

import com.mikeequinto.housechores.payload.request.RegistrationRequest;
import com.mikeequinto.housechores.payload.response.MessageResponse;
import com.mikeequinto.housechores.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/auth")
@AllArgsConstructor
public class AppUserController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<MessageResponse> register(@RequestBody RegistrationRequest request) {
        try{
            // Generate token
            registrationService.register(request);

            return new ResponseEntity(
                    new MessageResponse("user successfully created"),
                    HttpStatus.CREATED
            );
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(
                    new MessageResponse(e.getMessage()),
                    HttpStatus.CONFLICT
            );
        }
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
