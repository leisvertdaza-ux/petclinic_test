package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CASO 1 : Pruebas unitarias de la tabla vets
 */
@SpringBootTest
@Slf4j
public class VetServiceTest {

	@Autowired
	private VetService vetService;

	/**
	 *
	 */
	@Test
	public void testFindVetById() {

		final String FIRST_NAME_EXPECTED = "James";
		final String LAST_NAME_EXPECTED = "Carter";

		Integer ID = 1;

		Vet vet = null;

		try {
			vet = this.vetService.findById(ID);
		} catch (VetNotFoundException e) {
			fail(e.getMessage());
		}
		log.info("" + vet);

		assertEquals(FIRST_NAME_EXPECTED, vet.getFirstName());
		assertEquals(LAST_NAME_EXPECTED, vet.getLastName());
	}

	/**
	 *
	 */
	@Test
	public void testFindVetByLastName() {

		String FIND_LAST_NAME = "Carter";
		int SIZE_EXPECTED = 1;

		List<Vet> vets = this.vetService.findByLastName(FIND_LAST_NAME);

		assertEquals(SIZE_EXPECTED, vets.size());
	}

	/**
	 *
	 */
	@Test
	public void testFindInactiveVets() {

		int SIZE_EXPECTED = 1;

		List<Vet> vets = this.vetService.findByActive(false);

		assertEquals(SIZE_EXPECTED, vets.size());
	}

	/**
	 *
	 */
	@Test
	public void testCreateVet() {

		String FIRST_NAME = "Jaime";
		String LAST_NAME = "Gomez";
		String EMAIL = "jaime.gomez@petclinic.com";

		Vet vet = Vet.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.email(EMAIL)
				.phone("987654321")
				.active(true)
				.build();

		Vet newVet = this.vetService.create(vet);

		log.info("VET CREATED :" + newVet);

		assertNotNull(newVet.getId());
		assertEquals(FIRST_NAME, newVet.getFirstName());
		assertEquals(LAST_NAME, newVet.getLastName());
		assertEquals(EMAIL, newVet.getEmail());
	}

	/**
	 *
	 */
	@Test
	public void testUpdateVet() {

		String UP_FIRST_NAME = "Mario2";
		String UP_LAST_NAME = "Lopez2";
		String UP_PHONE = "999888777";

		Vet vet = Vet.builder()
				.firstName("Mario")
				.lastName("Lopez")
				.phone("111222333")
				.active(true)
				.build();

		// ------------ Create ---------------

		Vet vetCreated = this.vetService.create(vet);
		log.info(">>" + vetCreated);

		// ------------ Update ---------------

		vetCreated.setFirstName(UP_FIRST_NAME);
		vetCreated.setLastName(UP_LAST_NAME);
		vetCreated.setPhone(UP_PHONE);

		Vet upgradeVet = this.vetService.update(vetCreated);
		log.info(">>>>" + upgradeVet);

		//            EXPECTED        ACTUAL
		assertEquals(UP_FIRST_NAME, upgradeVet.getFirstName());
		assertEquals(UP_LAST_NAME, upgradeVet.getLastName());
		assertEquals(UP_PHONE, upgradeVet.getPhone());
	}

	/**
	 *
	 */
	@Test
	public void testDeleteVet() {

		// ------------ Create ---------------

		Vet vet = Vet.builder()
				.firstName("Pedro")
				.lastName("Ramos")
				.active(true)
				.build();

		Vet newVet = this.vetService.create(vet);
		log.info("" + newVet);

		// ------------ Delete ---------------

		try {
			this.vetService.delete(newVet.getId());
		} catch (VetNotFoundException e) {
			fail(e.getMessage());
		}

		// ------------ Validation ---------------

		try {
			this.vetService.findById(newVet.getId());
			assertTrue(false);
		} catch (VetNotFoundException e) {
			assertTrue(true);
		}
	}
}
