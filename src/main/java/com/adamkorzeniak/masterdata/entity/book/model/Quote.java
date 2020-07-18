package com.adamkorzeniak.masterdata.entity.book.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "quote", catalog = "book")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotNull
    @Column(name = "content")
    private String content;

    @Column(name = "comment")
    private String comment;

    @Column(name = "location")
    private String location;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
