package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;

import java.util.List;

public interface VetSpecialtyService {

	VetSpecialty assign(VetSpecialty vetSpecialty);

	VetSpecialty update(VetSpecialty vetSpecialty);

	void remove(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

	VetSpecialty findById(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

	List<VetSpecialty> findSpecialtiesByVet(Integer vetId);

	List<VetSpecialty> findVetsBySpecialty(Integer specialtyId);
}
