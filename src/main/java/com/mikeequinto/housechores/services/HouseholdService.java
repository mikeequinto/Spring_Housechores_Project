package com.mikeequinto.housechores.services;

import com.mikeequinto.housechores.models.Household;
import com.mikeequinto.housechores.payload.request.HouseholdRequest;
import com.mikeequinto.housechores.repo.HouseholdRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class HouseholdService {

    private final HouseholdRepository householdRepository;

    public Household createHousehold(HouseholdRequest request) {
        Household newHousehold = new Household(request.getName(), LocalDateTime.now());
        householdRepository.save(newHousehold);
        return newHousehold;
    }

}
