package com.adamkorzeniak.masterdata.diet.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.adamkorzeniak.masterdata.diet.model.UnitType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDTO {
	
	private Long id;

	@NotBlank
	@NotNull
	private String name;

	@NotBlank
	private String characteristic;

	@NotNull
	private String baseUnit;

	@NotNull
	private Double calories;

	@NotNull
	private Double carbs;

	@NotNull
	private Double fats;

	@NotNull
	private Double proteins;

	private Double roughage;

	private Double salt;

}
