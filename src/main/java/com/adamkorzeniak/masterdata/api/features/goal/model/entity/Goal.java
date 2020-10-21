package com.adamkorzeniak.masterdata.api.features.goal.model.entity;

import com.adamkorzeniak.masterdata.api.features.goal.service.GoalUtils;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "goal", catalog = "goals")
public class Goal {

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

    @NotNull
    @Column(name = "significance")
    private Double significance;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id")
    private Set<GoalConfiguration> goalConfigurations;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id")
    private Set<GoalElement> goalElements;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id")
    private Set<GoalRatingConfiguration> goalRatingConfigurations ;

    public Optional<GoalRatingConfiguration> getRatingConfiguration(LocalDate date) {
        List<GoalRatingConfiguration> ratingConfList = goalRatingConfigurations.stream()
//                .filter(ratingConf -> GoalUtils.isDateValid(date, ratingConf.getValidFrom(), ratingConf.getValidTo()))
                .collect(Collectors.toList());
        if (ratingConfList.size() > 1) {
            throw new RuntimeException();
        }
        if (ratingConfList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ratingConfList.get(0));
    }
}
