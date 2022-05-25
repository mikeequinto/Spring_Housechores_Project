package com.mikeequinto.housechores.services;
import com.mikeequinto.housechores.models.AppUser;
import com.mikeequinto.housechores.payload.request.LoginRequest;
import com.mikeequinto.housechores.repo.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppUser login(LoginRequest request) {
        Optional<AppUser> appUser = appUserRepository.findByEmail(request.getEmail());
        if (appUser.isEmpty()) {
            throw new IllegalStateException("email or password is incorrect");
        }
        AppUser existingUser = appUser.get();
        if (!bCryptPasswordEncoder.matches(request.getPassword(), existingUser.getPassword())) {
            throw new IllegalStateException("email or password is incorrect");
        }
        return existingUser;
    }
}
