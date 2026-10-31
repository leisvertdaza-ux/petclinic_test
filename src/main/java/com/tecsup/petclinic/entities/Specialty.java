package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de la tabla specialties
 */
@Entity(name = "specialties")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Specialty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "office")
	private String office;

	@Column(name = "h_open")
	private Integer hOpen;

	@Column(name = "h_close")
	private Integer hClose;
}
