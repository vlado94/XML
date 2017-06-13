package com.banka;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
public class Banka {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@Column(unique = true/*, columnDefinition = "CHAR(3)"*/)
	@NotBlank
	private String kodBanke;

	@Column(unique = true/*, columnDefinition = "CHAR(10)"*/)
	@NotBlank
	private String PIB;

	@Column//(length = 120)
	@NotBlank
	private String ime;

	/*@Column//(length = 120)
	@NotBlank
	private String adresa;

	@Column//(length = 128)
	@Email
	private String mail;

	@Column//(length = 128)
	private String web;
	
	@Column//(length = 20)
	private String telefon;

	@Column//(length = 20)
	private String fax;
	*/
	@Column
	private String uri;
	
	@Column//(length = 20)
	private String obracunskiRacun;

}