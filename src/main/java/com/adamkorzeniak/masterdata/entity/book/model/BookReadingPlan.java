package com.adamkorzeniak.masterdata.entity.book.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "plan", catalog = "book")
public class BookReadingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Min(0)
    @Max(100)
    @Column(name = "priority")
    private Integer priority;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;
}
