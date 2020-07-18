package com.adamkorzeniak.masterdata.entity.software.model;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"platforms", "reviews"})
@Entity
@Table(name = "application", catalog = "software")
public class Application extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "name")
    private String type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "application_platform", catalog = "software", joinColumns = @JoinColumn(name = "application_id"), inverseJoinColumns = @JoinColumn(name = "platform_id"))
    private Set<ApplicationPlatform> platforms;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_id")
    private Set<ApplicationReview> reviews;
}