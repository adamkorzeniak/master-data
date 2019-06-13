package com.adamkorzeniak.masterdata.features.diet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Entity
@Table(name = "diet__products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank
	@NotNull
	@Column(name = "name")
	private String name;

	@NotBlank
	@Column(name = "characteristic")
	private String characteristic;

	@NotNull
	@Column(name = "baseUnit")
	private UnitType baseUnit;

	@NotNull
	@Column(name = "calories")
	private Double calories;

	@NotNull
	@Column(name = "carbs")
	private Double carbs;

	@NotNull
	@Column(name = "fats")
	private Double fats;

	@NotNull
	@Column(name = "proteins")
	private Double proteins;

	@Column(name = "roughage")
	private Double roughage;

	@Column(name = "salt")
	private Double salt;

}
