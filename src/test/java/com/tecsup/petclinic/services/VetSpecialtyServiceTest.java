package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CASO 6 : Pruebas unitarias de la tabla vet_specialties
 */
@SpringBootTest
@Slf4j
public class VetSpecialtyServiceTest {

	@Autowired
	private VetSpecialtyService vetSpecialtyService;

	/**
	 *
	 */
	@Test
	public void testFindVetSpecialtyById() {

		final Integer VET_ID = 2;
		final Integer SPECIALTY_ID = 1;
		final Integer YEARS_EXPECTED = 8;

		VetSpecialty vetSpecialty = null;

		try {
			vetSpecialty = this.vetSpecialtyService.findById(VET_ID, SPECIALTY_ID);
		} catch (VetSpecialtyNotFoundException e) {
			fail(e.getMessage());
		}
		log.info("" + vetSpecialty);

		assertEquals(YEARS_EXPECTED, vetSpecialty.getYearsExperience());
		assertTrue(vetSpecialty.getPrimary());
	}

	/**
	 * Especialidades de un veterinario
	 */
	@Test
	public void testFindSpecialtiesByVet() {

		Integer VET_ID = 3;
		int SIZE_EXPECTED = 2;

		List<VetSpecialty> specialties = this.vetSpecialtyService.findSpecialtiesByVet(VET_ID);

		assertEquals(SIZE_EXPECTED, specialties.size());
	}

	/**
	 * Veterinarios de una especialidad
	 */
	@Test
	public void testFindVetsBySpecialty() {

		Integer SPECIALTY_ID = 1;
		int SIZE_EXPECTED = 2;

		List<VetSpecialty> vets = this.vetSpecialtyService.findVetsBySpecialty(SPECIALTY_ID);

		assertEquals(SIZE_EXPECTED, vets.size());
	}

	/**
	 *
	 */
	@Test
	public void testFindSpecialtiesByVet_Empty() {

		Integer VET_ID = 5000;   // veterinario sin especialidades asignadas

		List<VetSpecialty> specialties = this.vetSpecialtyService.findSpecialtiesByVet(VET_ID);

		assertTrue(specialties.isEmpty());
		log.info("Vet {} sin especialidades: {}", VET_ID, specialties);
	}

	/**
	 * Asignar especialidad a un veterinario
	 */
	@Test
	public void testAssignSpecialtyToVet() {

		Integer VET_ID = 1;
		Integer SPECIALTY_ID = 3;
		LocalDate CERT_DATE = LocalDate.of(2020, 5, 10);
		Integer YEARS = 4;

		VetSpecialty vetSpecialty = VetSpecialty.builder()
				.vetId(VET_ID)
				.specialtyId(SPECIALTY_ID)
				.certificationDate(CERT_DATE)
				.yearsExperience(YEARS)
				.primary(false)
				.notes("Dental certification")
				.build();

		VetSpecialty newVetSpecialty = this.vetSpecialtyService.assign(vetSpecialty);

		log.info("ASSIGNED :" + newVetSpecialty);

		assertEquals(VET_ID, newVetSpecialty.getVetId());
		assertEquals(SPECIALTY_ID, newVetSpecialty.getSpecialtyId());
		assertEquals(CERT_DATE, newVetSpecialty.getCertificationDate());
		assertEquals(YEARS, newVetSpecialty.getYearsExperience());
	}

	/**
	 *
	 */
	@Test
	public void testUpdateVetSpecialty() {

		Integer UP_YEARS = 10;
		LocalDate UP_CERT_DATE = LocalDate.of(2019, 1, 15);
		String UP_NOTES = "Updated certification";

		VetSpecialty vetSpecialty = VetSpecialty.builder()
				.vetId(4)
				.specialtyId(3)
				.certificationDate(LocalDate.of(2021, 7, 1))
				.yearsExperience(2)
				.primary(false)
				.notes("Initial")
				.build();

		// ------------ Assign ---------------

		VetSpecialty created = this.vetSpecialtyService.assign(vetSpecialty);
		log.info(">>" + created);

		// ------------ Update ---------------

		created.setYearsExperience(UP_YEARS);
		created.setCertificationDate(UP_CERT_DATE);
		created.setNotes(UP_NOTES);

		VetSpecialty upgrade = this.vetSpecialtyService.update(created);
		log.info(">>>>" + upgrade);

		//            EXPECTED        ACTUAL
		assertEquals(UP_YEARS, upgrade.getYearsExperience());
		assertEquals(UP_CERT_DATE, upgrade.getCertificationDate());
		assertEquals(UP_NOTES, upgrade.getNotes());
	}

	/**
	 * Eliminar la relación veterinario-especialidad
	 */
	@Test
	public void testRemoveSpecialtyFromVet() {

		Integer VET_ID = 6;
		Integer SPECIALTY_ID = 2;

		// ------------ Assign ---------------

		VetSpecialty vetSpecialty = VetSpecialty.builder()
				.vetId(VET_ID)
				.specialtyId(SPECIALTY_ID)
				.yearsExperience(1)
				.primary(false)
				.build();

		this.vetSpecialtyService.assign(vetSpecialty);

		// ------------ Remove ---------------

		try {
			this.vetSpecialtyService.remove(VET_ID, SPECIALTY_ID);
		} catch (VetSpecialtyNotFoundException e) {
			fail(e.getMessage());
		}

		// ------------ Validation ---------------

		try {
			this.vetSpecialtyService.findById(VET_ID, SPECIALTY_ID);
			assertTrue(false);
		} catch (VetSpecialtyNotFoundException e) {
			assertTrue(true);
		}
	}
}
