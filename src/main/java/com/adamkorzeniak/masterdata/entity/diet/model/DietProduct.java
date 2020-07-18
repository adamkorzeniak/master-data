package com.adamkorzeniak.masterdata.entity.diet.model;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "product", catalog = "diet")
public class DietProduct extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

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
