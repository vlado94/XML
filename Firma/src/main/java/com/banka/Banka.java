package com.banka;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
public class Banka {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@Column(unique = true, columnDefinition = "CHAR(3)")
	@NotBlank
	private String bankCode;

	@Column(unique = true, columnDefinition = "CHAR(10)")
	@NotBlank
	private String PIB;

	@Column(length = 120)
	@NotBlank
	private String name;

	@Column(length = 120)
	@NotBlank
	private String address;

	@Column(length = 128)
	@Email
	private String email;

	@Column(length = 128)
	private String web;
	
	@Column(length = 20)
	private String phone;
	
	@Column(length = 20)
	private String fax;
	/*
	@JsonIgnore
	@OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
	private List<Firma> firms;
*/
}