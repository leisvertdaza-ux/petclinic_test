package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CASO 3 : Pruebas unitarias de la tabla specialties
 */
@SpringBootTest
@Slf4j
public class SpecialtyServiceTest {

	@Autowired
	private SpecialtyService specialtyService;

	/**
	 *
	 */
	@Test
	public void testFindSpecialtyById() {

		final String NAME_EXPECTED = "radiology";
		final String OFFICE_EXPECTED = "Farewell";

		Integer ID = 1;

		Specialty specialty = null;

		try {
			specialty = this.specialtyService.findById(ID);
		} catch (SpecialtyNotFoundException e) {
			fail(e.getMessage());
		}
		log.info("" + specialty);

		assertEquals(NAME_EXPECTED, specialty.getName());
		assertEquals(OFFICE_EXPECTED, specialty.getOffice());
	}

	/**
	 *
	 */
	@Test
	public void testFindSpecialtyByName() {

		String FIND_NAME = "surgery";
		int SIZE_EXPECTED = 1;

		List<Specialty> specialties = this.specialtyService.findByName(FIND_NAME);

		assertEquals(SIZE_EXPECTED, specialties.size());
	}

	/**
	 *
	 */
	@Test
	public void testFindSpecialtyByOffice() {

		String FIND_OFFICE = "Terranova";
		int SIZE_EXPECTED = 1;

		List<Specialty> specialties = this.specialtyService.findByOffice(FIND_OFFICE);

		assertEquals(SIZE_EXPECTED, specialties.size());
	}

	/**
	 *
	 */
	@Test
	public void testCreateSpecialty() {

		String NAME = "cardiology";
		String OFFICE = "Lima Norte";
		Integer H_OPEN = 8;
		Integer H_CLOSE = 16;

		Specialty specialty = Specialty.builder()
				.name(NAME)
				.office(OFFICE)
				.hOpen(H_OPEN)
				.hClose(H_CLOSE)
				.build();

		Specialty newSpecialty = this.specialtyService.create(specialty);

		log.info("SPECIALTY CREATED :" + newSpecialty);

		assertNotNull(newSpecialty.getId());
		assertEquals(NAME, newSpecialty.getName());
		assertEquals(OFFICE, newSpecialty.getOffice());
		assertEquals(H_OPEN, newSpecialty.getHOpen());
		assertEquals(H_CLOSE, newSpecialty.getHClose());
	}

	/**
	 *
	 */
	@Test
	public void testUpdateSpecialty() {

		String UP_NAME = "dermatology2";
		String UP_OFFICE = "Surco";
		Integer UP_H_OPEN = 10;
		Integer UP_H_CLOSE = 20;

		Specialty specialty = Specialty.builder()
				.name("dermatology")
				.office("Miraflores")
				.hOpen(9)
				.hClose(17)
				.build();

		// ------------ Create ---------------

		Specialty specialtyCreated = this.specialtyService.create(specialty);
		log.info(">>" + specialtyCreated);

		// ------------ Update ---------------

		specialtyCreated.setName(UP_NAME);
		specialtyCreated.setOffice(UP_OFFICE);
		specialtyCreated.setHOpen(UP_H_OPEN);
		specialtyCreated.setHClose(UP_H_CLOSE);

		Specialty upgradeSpecialty = this.specialtyService.update(specialtyCreated);
		log.info(">>>>" + upgradeSpecialty);

		//            EXPECTED        ACTUAL
		assertEquals(UP_NAME, upgradeSpecialty.getName());
		assertEquals(UP_OFFICE, upgradeSpecialty.getOffice());
		assertEquals(UP_H_OPEN, upgradeSpecialty.getHOpen());
		assertEquals(UP_H_CLOSE, upgradeSpecialty.getHClose());
	}

	/**
	 *
	 */
	@Test
	public void testDeleteSpecialty() {

		// ------------ Create ---------------

		Specialty specialty = Specialty.builder()
				.name("oncology")
				.office("San Isidro")
				.hOpen(8)
				.hClose(14)
				.build();

		Specialty newSpecialty = this.specialtyService.create(specialty);
		log.info("" + newSpecialty);

		// ------------ Delete ---------------

		try {
			this.specialtyService.delete(newSpecialty.getId());
		} catch (SpecialtyNotFoundException e) {
			fail(e.getMessage());
		}

		// ------------ Validation ---------------

		try {
			this.specialtyService.findById(newSpecialty.getId());
			assertTrue(false);
		} catch (SpecialtyNotFoundException e) {
			assertTrue(true);
		}
	}
}
