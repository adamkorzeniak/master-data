package com.adamkorzeniak.masterdata.entity.astronomy.model;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "observation_item", catalog = "astronomy")
public class ObservationItem extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "optic_tool")
    private String opticTool;

    @NotEmpty
    @Column(name = "notes")
    private String notes;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "end")
    private LocalDateTime end;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "object_id")
    private Object object;
}
