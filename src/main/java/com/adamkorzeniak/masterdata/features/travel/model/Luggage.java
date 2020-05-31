package com.adamkorzeniak.masterdata.features.travel.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "luggages", catalog = "travel")
public class Luggage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @Size(min = 11, max = 12)
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotEmpty
    @Column(name = "email")
    private String email;

    @NotEmpty
    @Column(name = "work_email")
    private String workEmail;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "nameday")
    private LocalDate nameday;
}
