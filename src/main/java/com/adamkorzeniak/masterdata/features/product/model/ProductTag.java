package com.adamkorzeniak.masterdata.features.product.model;

import com.adamkorzeniak.masterdata.api.basic.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"id"})
@EqualsAndHashCode(of = "name")
@Entity
@Table(name = "tag", catalog = "product")
public class ProductTag extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

}
