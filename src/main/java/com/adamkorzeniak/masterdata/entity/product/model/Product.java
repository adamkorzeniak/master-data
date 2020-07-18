package com.adamkorzeniak.masterdata.entity.product.model;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"tags", "reviews"})
@Entity
@Table(name = "product", catalog = "product")
public class Product extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @NotEmpty
    @Column(name = "type")
    private String type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_tag", catalog = "product", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<ProductTag> tags;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private Set<ProductReview> reviews;
}
