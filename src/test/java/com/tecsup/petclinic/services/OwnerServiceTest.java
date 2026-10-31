package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CASO 2 : Pruebas unitarias de la tabla owners
 */
@SpringBootTest
@Slf4j
public class OwnerServiceTest {

	@Autowired
	private OwnerService ownerService;

	/**
	 *
	 */
	@Test
	public void testFindOwnerById() {

		final String FIRST_NAME_EXPECTED = "George";
		final String CITY_EXPECTED = "Madison";

		Integer ID = 1;

		Owner owner = null;

		try {
			owner = this.ownerService.findById(ID);
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}
		log.info("" + owner);

		assertEquals(FIRST_NAME_EXPECTED, owner.getFirstName());
		assertEquals(CITY_EXPECTED, owner.getCity());
	}

	/**
	 *
	 */
	@Test
	public void testFindOwnerByLastName() {

		String FIND_LAST_NAME = "Davis";
		int SIZE_EXPECTED = 2;

		List<Owner> owners = this.ownerService.findByLastName(FIND_LAST_NAME);

		assertEquals(SIZE_EXPECTED, owners.size());
	}

	/**
	 *
	 */
	@Test
	public void testFindOwnerByCity() {

		String FIND_CITY = "Madison";
		int SIZE_EXPECTED = 4;

		List<Owner> owners = this.ownerService.findByCity(FIND_CITY);

		assertEquals(SIZE_EXPECTED, owners.size());
	}

	/**
	 *
	 */
	@Test
	public void testCreateOwner() {

		String FIRST_NAME = "Rosa";
		String LAST_NAME = "Quispe";
		String CITY = "Lima";

		Owner owner = Owner.builder()
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.address("Av. Cascanueces 2221")
				.city(CITY)
				.telephone("987123456")
				.build();

		Owner newOwner = this.ownerService.create(owner);

		log.info("OWNER CREATED :" + newOwner);

		assertNotNull(newOwner.getId());
		assertEquals(FIRST_NAME, newOwner.getFirstName());
		assertEquals(LAST_NAME, newOwner.getLastName());
		assertEquals(CITY, newOwner.getCity());
	}

	/**
	 *
	 */
	@Test
	public void testUpdateOwner() {

		String UP_FIRST_NAME = "Luis2";
		String UP_ADDRESS = "Calle Nueva 123";
		String UP_CITY = "Arequipa";

		Owner owner = Owner.builder()
				.firstName("Luis")
				.lastName("Torres")
				.address("Calle Antigua 456")
				.city("Lima")
				.telephone("912345678")
				.build();

		// ------------ Create ---------------

		Owner ownerCreated = this.ownerService.create(owner);
		log.info(">>" + ownerCreated);

		// ------------ Update ---------------

		ownerCreated.setFirstName(UP_FIRST_NAME);
		ownerCreated.setAddress(UP_ADDRESS);
		ownerCreated.setCity(UP_CITY);

		Owner upgradeOwner = this.ownerService.update(ownerCreated);
		log.info(">>>>" + upgradeOwner);

		//            EXPECTED        ACTUAL
		assertEquals(UP_FIRST_NAME, upgradeOwner.getFirstName());
		assertEquals(UP_ADDRESS, upgradeOwner.getAddress());
		assertEquals(UP_CITY, upgradeOwner.getCity());
	}

	/**
	 *
	 */
	@Test
	public void testDeleteOwner() {

		// ------------ Create ---------------

		Owner owner = Owner.builder()
				.firstName("Ana")
				.lastName("Flores")
				.city("Lima")
				.build();

		Owner newOwner = this.ownerService.create(owner);
		log.info("" + newOwner);

		// ------------ Delete ---------------

		try {
			this.ownerService.delete(newOwner.getId());
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}

		// ------------ Validation ---------------

		try {
			this.ownerService.findById(newOwner.getId());
			assertTrue(false);
		} catch (OwnerNotFoundException e) {
			assertTrue(true);
		}
	}
}
