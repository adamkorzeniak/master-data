package com.adamkorzeniak.masterdata.features.diet.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
//@Entity
//@Table(name = "diet__consumption_goals")
public class ConsumptionGoal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank
	@NotNull
	@Column(name = "calories")
	private Double calories;

	@Column(name = "carbs")
	private Double carbs;

	@Column(name = "fats")
	private Double fats;

	@Column(name = "proteins")
	private Double proteins;

}
