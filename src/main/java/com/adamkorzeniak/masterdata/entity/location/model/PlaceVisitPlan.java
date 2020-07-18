package com.adamkorzeniak.masterdata.entity.location.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "plan", catalog = "location")
public class PlaceVisitPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Min(0)
    @Max(100)
    @Column(name = "priority")
    private Integer priority;

    @NotEmpty
    @Column(name = "comment")
    private String comment;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;
}
