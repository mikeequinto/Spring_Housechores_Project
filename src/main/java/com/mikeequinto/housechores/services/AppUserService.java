package com.mikeequinto.housechores.services;

import com.mikeequinto.housechores.models.AppUser;
import com.mikeequinto.housechores.repo.AppUserRepository;
import com.mikeequinto.housechores.security.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE = "user with email %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        if (userExists) {
            AppUser existingUser = appUserRepository.findByEmail(appUser.getEmail()).get();

            if (existingUser.getEnabled()) {
                // Update user data and return token
                existingUser.setName(appUser.getName());
                existingUser.setPassword(encodedPassword);
                appUserRepository.save(existingUser);

                confirmationToken.setAppUser(existingUser);
                confirmationTokenService.saveConfirmationToken(confirmationToken);

                return token;
            }

            throw new IllegalStateException("email already taken");
        }

        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
