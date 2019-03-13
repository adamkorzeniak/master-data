package com.adamkorzeniak.masterdata.diet.model.dto;

import javax.persistence.Column;
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
public class FoodUnitDTO {
	
	private Long id;

	@NotBlank
	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	private ProductSmallDTO product;

	@NotNull
	private Double multiplier;

	@Getter
	@Setter
	@NoArgsConstructor
	@ToString
	private class ProductSmallDTO {
		private Long id;

		@NotBlank
		@NotNull
		private String name;
	}
}
