package com.mikeequinto.housechores.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Household {

    @SequenceGenerator(
            name = "household_sequence",
            sequenceName = "household_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "household_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "app_users_households_associations",
            joinColumns = @JoinColumn(name = "household_id"),
            inverseJoinColumns = @JoinColumn(name = "app_user_id")
    )
    private List<AppUser> appUsers = new ArrayList<>();

    public Household (String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
