package com.banka;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
public class Banka {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BANKA_ID")
	private Long id;
	
	@NotBlank
	@Column
	private String naziv;
	
	@NotNull
	@Column(length=3)
	private Integer kod;
	
	@NotNull
	@Column(length=8)
	private String swiftKod;
}
