package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * Entidad de la tabla visits
 */
@Entity(name = "visits")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Visit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "pet_id")
	private Integer petId;

	@Column(name = "vet_id")
	private Integer vetId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "visit_date")
	private LocalDate visitDate;

	@Column(name = "description")
	private String description;

	@Column(name = "cost")
	private BigDecimal cost;
}
