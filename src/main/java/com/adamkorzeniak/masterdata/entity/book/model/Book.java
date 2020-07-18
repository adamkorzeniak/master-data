package com.adamkorzeniak.masterdata.entity.book.model;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
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
@EqualsAndHashCode(exclude = {"tags", "items", "plans", "reviews", "quotes"})
@Entity
@Table(name = "book", catalog = "book")
public class Book extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "title")
    private String title;

    @NotEmpty
    @Column(name = "title_en")
    private String titleEn;

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
    @JoinTable(name = "book_tag", catalog = "book", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<BookTag> tags;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private Set<BookReview> reviews;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private Set<BookReadingPlan> plans;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private Set<BookItem> items;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Set<Quote> quotes;
}
