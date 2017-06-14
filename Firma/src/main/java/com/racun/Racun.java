package com.racun;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Racun {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RACUN_ID")
	private Long id;

	//@NotBlank
	@Column
	private String brojRacuna; // 18

	@Column
	private BigDecimal trenutnoStanje;

	@Column
	private BigDecimal rezervisano;
}
