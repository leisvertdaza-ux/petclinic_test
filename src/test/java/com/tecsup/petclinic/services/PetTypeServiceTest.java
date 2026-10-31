package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CASO 5 : Pruebas unitarias de la tabla types
 */
@SpringBootTest
@Slf4j
public class PetTypeServiceTest {

	@Autowired
	private PetTypeService petTypeService;

	/**
	 *
	 */
	@Test
	public void testFindTypeById() {

		final String NAME_EXPECTED = "dog";
		final String DESCRIPTION_EXPECTED = "Domestic canine";

		Integer ID = 2;

		PetType type = null;

		try {
			type = this.petTypeService.findById(ID);
		} catch (PetTypeNotFoundException e) {
			fail(e.getMessage());
		}
		log.info("" + type);

		assertEquals(NAME_EXPECTED, type.getName());
		assertEquals(DESCRIPTION_EXPECTED, type.getDescription());
	}

	/**
	 *
	 */
	@Test
	public void testFindTypeByName() {

		String FIND_NAME = "hamster";
		int SIZE_EXPECTED = 1;

		List<PetType> types = this.petTypeService.findByName(FIND_NAME);

		assertEquals(SIZE_EXPECTED, types.size());
	}

	/**
	 *
	 */
	@Test
	public void testFindInactiveTypes() {

		int SIZE_EXPECTED = 1;

		List<PetType> types = this.petTypeService.findByActive(false);

		assertEquals(SIZE_EXPECTED, types.size());
		assertEquals("snake", types.get(0).getName());
	}

	/**
	 *
	 */
	@Test
	public void testCreateType() {

		String NAME = "ferret";
		String DESCRIPTION = "Small mustelid";
		String SIZE_CATEGORY = "small";
		Integer AVERAGE_LIFESPAN = 7;

		PetType type = PetType.builder()
				.name(NAME)
				.description(DESCRIPTION)
				.active(true)
				.sizeCategory(SIZE_CATEGORY)
				.averageLifespan(AVERAGE_LIFESPAN)
				.careLevel("high")
				.build();

		PetType newType = this.petTypeService.create(type);

		log.info("TYPE CREATED :" + newType);

		assertNotNull(newType.getId());
		assertEquals(NAME, newType.getName());
		assertEquals(DESCRIPTION, newType.getDescription());
		assertEquals(SIZE_CATEGORY, newType.getSizeCategory());
		assertEquals(AVERAGE_LIFESPAN, newType.getAverageLifespan());
	}

	/**
	 *
	 */
	@Test
	public void testUpdateType() {

		String UP_NAME = "parrot2";
		String UP_DESCRIPTION = "Big tropical bird";
		String UP_CARE_LEVEL = "high";

		PetType type = PetType.builder()
				.name("parrot")
				.description("Tropical bird")
				.active(true)
				.sizeCategory("small")
				.averageLifespan(40)
				.careLevel("medium")
				.build();

		// ------------ Create ---------------

		PetType typeCreated = this.petTypeService.create(type);
		log.info(">>" + typeCreated);

		// ------------ Update ---------------

		typeCreated.setName(UP_NAME);
		typeCreated.setDescription(UP_DESCRIPTION);
		typeCreated.setCareLevel(UP_CARE_LEVEL);

		PetType upgradeType = this.petTypeService.update(typeCreated);
		log.info(">>>>" + upgradeType);

		//            EXPECTED        ACTUAL
		assertEquals(UP_NAME, upgradeType.getName());
		assertEquals(UP_DESCRIPTION, upgradeType.getDescription());
		assertEquals(UP_CARE_LEVEL, upgradeType.getCareLevel());
	}

	/**
	 *
	 */
	@Test
	public void testDeleteType() {

		// ------------ Create ---------------

		PetType type = PetType.builder()
				.name("fish")
				.description("Aquarium fish")
				.active(true)
				.build();

		PetType newType = this.petTypeService.create(type);
		log.info("" + newType);

		// ------------ Delete ---------------

		try {
			this.petTypeService.delete(newType.getId());
		} catch (PetTypeNotFoundException e) {
			fail(e.getMessage());
		}

		// ------------ Validation ---------------

		try {
			this.petTypeService.findById(newType.getId());
			assertTrue(false);
		} catch (PetTypeNotFoundException e) {
			assertTrue(true);
		}
	}
}
