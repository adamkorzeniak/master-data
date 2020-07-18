package com.adamkorzeniak.masterdata.features.book.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "item", catalog = "book")
public class BookItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "language")
    private String language;

    @Column(name = "location")
    private String location;
}
