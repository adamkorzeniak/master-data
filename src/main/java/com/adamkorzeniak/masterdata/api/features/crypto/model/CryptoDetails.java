package com.adamkorzeniak.masterdata.api.features.crypto.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "symbol")
@Entity
@Table(name = "crypto", catalog = "crypto")
public class CryptoDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotNull
    @Column(name = "cmc_id")
    private Long cmcId;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "symbol")
    private String symbol;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;

}
