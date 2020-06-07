package com.adamkorzeniak.masterdata.features.diet.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "product", catalog = "diet")
public class DietProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "characteristic")
    private String characteristic;

    @NotNull
    @Column(name = "calories")
    private Double calories;

    @NotNull
    @Column(name = "proteins")
    private Double proteins;

    @NotNull
    @Column(name = "fats")
    private Double fats;

    @NotNull
    @Column(name = "carbs")
    private Double carbs;

    @Column(name = "roughage")
    private Double roughage;

    @Column(name = "salt")
    private Double salt;

}
