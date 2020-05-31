package com.adamkorzeniak.masterdata.features.product.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"tags", "plans", "reviews"})
@Entity
@Table(name = "product", catalog = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "title")
    private String title;

    @NotEmpty
    @Column(name = "title_en")
    private String title_en;

    @NotEmpty
    @Column(name = "author")
    private String author;

    @NotEmpty
    @Column(name = "series")
    private String series;

    @Min(1800)
    @Max(2999)
    @NotNull
    @Column(name = "year")
    private Integer year;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_tag", catalog = "product", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<ProductTag> tags;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private Set<ProductReview> reviews;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private Set<ProductTestingPlan> plans;
}
