package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CASO 4 : Pruebas unitarias de la tabla visits
 */
@SpringBootTest
@Slf4j
public class VisitServiceTest {

	@Autowired
	private VisitService visitService;

	/**
	 *
	 */
	@Test
	public void testFindVisitById() {

		final String DESCRIPTION_EXPECTED = "rabies shot";
		final Integer PET_ID_EXPECTED = 7;

		Integer ID = 1;

		Visit visit = null;

		try {
			visit = this.visitService.findById(ID);
		} catch (VisitNotFoundException e) {
			fail(e.getMessage());
		}
		log.info("" + visit);

		assertEquals(DESCRIPTION_EXPECTED, visit.getDescription());
		assertEquals(PET_ID_EXPECTED, visit.getPetId());
		assertEquals(LocalDate.of(2010, 3, 4), visit.getVisitDate());
	}

	/**
	 *
	 */
	@Test
	public void testFindVisitByPetId() {

		Integer PET_ID = 7;
		int SIZE_EXPECTED = 2;

		List<Visit> visits = this.visitService.findByPetId(PET_ID);

		assertEquals(SIZE_EXPECTED, visits.size());
	}

	/**
	 *
	 */
	@Test
	public void testFindVisitByVetId() {

		Integer VET_ID = 2;
		int SIZE_EXPECTED = 2;

		List<Visit> visits = this.visitService.findByVetId(VET_ID);

		assertEquals(SIZE_EXPECTED, visits.size());
	}

	/**
	 *
	 */
	@Test
	public void testCreateVisit() {

		Integer PET_ID = 1;
		Integer VET_ID = 1;
		LocalDate VISIT_DATE = LocalDate.of(2025, 10, 20);
		String DESCRIPTION = "vaccination";
		BigDecimal COST = new BigDecimal("80.50");

		Visit visit = Visit.builder()
				.petId(PET_ID)
				.vetId(VET_ID)
				.visitDate(VISIT_DATE)
				.description(DESCRIPTION)
				.cost(COST)
				.build();

		Visit newVisit = this.visitService.create(visit);

		log.info("VISIT CREATED :" + newVisit);

		assertNotNull(newVisit.getId());
		assertEquals(PET_ID, newVisit.getPetId());
		assertEquals(VET_ID, newVisit.getVetId());
		assertEquals(DESCRIPTION, newVisit.getDescription());
		assertEquals(0, COST.compareTo(newVisit.getCost()));
	}

	/**
	 *
	 */
	@Test
	public void testUpdateVisit() {

		String UP_DESCRIPTION = "general checkup 2";
		BigDecimal UP_COST = new BigDecimal("120.00");
		LocalDate UP_VISIT_DATE = LocalDate.of(2025, 11, 5);

		Visit visit = Visit.builder()
				.petId(1)
				.vetId(1)
				.visitDate(LocalDate.of(2025, 11, 1))
				.description("general checkup")
				.cost(new BigDecimal("60.00"))
				.build();

		// ------------ Create ---------------

		Visit visitCreated = this.visitService.create(visit);
		log.info(">>" + visitCreated);

		// ------------ Update ---------------

		visitCreated.setDescription(UP_DESCRIPTION);
		visitCreated.setCost(UP_COST);
		visitCreated.setVisitDate(UP_VISIT_DATE);

		Visit upgradeVisit = this.visitService.update(visitCreated);
		log.info(">>>>" + upgradeVisit);

		//            EXPECTED        ACTUAL
		assertEquals(UP_DESCRIPTION, upgradeVisit.getDescription());
		assertEquals(0, UP_COST.compareTo(upgradeVisit.getCost()));
		assertEquals(UP_VISIT_DATE, upgradeVisit.getVisitDate());
	}

	/**
	 *
	 */
	@Test
	public void testDeleteVisit() {

		// ------------ Create ---------------

		Visit visit = Visit.builder()
				.petId(1)
				.vetId(1)
				.visitDate(LocalDate.of(2025, 12, 1))
				.description("deworming")
				.cost(new BigDecimal("35.00"))
				.build();

		Visit newVisit = this.visitService.create(visit);
		log.info("" + newVisit);

		// ------------ Delete ---------------

		try {
			this.visitService.delete(newVisit.getId());
		} catch (VisitNotFoundException e) {
			fail(e.getMessage());
		}

		// ------------ Validation ---------------

		try {
			this.visitService.findById(newVisit.getId());
			assertTrue(false);
		} catch (VisitNotFoundException e) {
			assertTrue(true);
		}
	}
}
