package com.firma;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

import com.banka.Banka;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.racun.Racun;

import lombok.Data;

@Data
@Entity
public class Firma {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FIRMA_ID")
	private Long id;
	
	@NotBlank
	@Column
	private String naziv;
	
	@NotBlank
	@Column
	private String adresa;
	
	@NotBlank
	@Column
	private String PIB;
	
	@NotBlank
	@Column
	private String uri;
	
	@OneToMany
	@JoinTable(name = "FIRMA_RACUNI", joinColumns = @JoinColumn(name = "FIRMA_ID"), inverseJoinColumns = @JoinColumn(name = "RACUN_ID"))
	private List<Racun> racuni; // kursna lista
	

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "POSLOVNI_SARADNICI", joinColumns = @JoinColumn(name = "FIRMA1_ID"), inverseJoinColumns = @JoinColumn(name = "FIRMA2_ID"))
    protected List<Firma> poslovniSaradnici = new ArrayList<Firma>();

	@ManyToOne
	@JoinColumn(name = "BANKA_ID")
    protected Banka banka;
	
}
