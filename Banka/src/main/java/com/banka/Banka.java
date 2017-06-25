package com.banka;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

import com.racun.Racun;

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

	@Column(unique = true)
	@NotBlank
	private String swiftKod;

	@Column//(length = 120)
	@NotBlank
	private String ime;

	@Column
	private String uri;

	@ManyToOne
	@JoinColumn(name = "OBRACUNSKI_ID")
	private Racun obracunskiRacun;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "BANKA_RACUNI", joinColumns = @JoinColumn(name = "BANKA_ID"), inverseJoinColumns = @JoinColumn(name = "RACUN_ID"))
	private List<Racun> racuni;

}