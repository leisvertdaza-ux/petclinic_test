package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

	private final VetSpecialtyRepository vetSpecialtyRepository;

	@Override
	public VetSpecialty assign(VetSpecialty vetSpecialty) {
		return vetSpecialtyRepository.save(vetSpecialty);
	}

	@Override
	public VetSpecialty update(VetSpecialty vetSpecialty) {
		return vetSpecialtyRepository.save(vetSpecialty);
	}

	@Override
	public void remove(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
		VetSpecialty vetSpecialty = findById(vetId, specialtyId);
		vetSpecialtyRepository.delete(vetSpecialty);
	}

	@Override
	public VetSpecialty findById(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
		return vetSpecialtyRepository.findById(new VetSpecialtyId(vetId, specialtyId))
				.orElseThrow(() -> new VetSpecialtyNotFoundException("Record not found...!"));
	}

	@Override
	public List<VetSpecialty> findSpecialtiesByVet(Integer vetId) {
		List<VetSpecialty> list = vetSpecialtyRepository.findByVetId(vetId);
		list.forEach(item -> log.info("{}", item));
		return list;
	}

	@Override
	public List<VetSpecialty> findVetsBySpecialty(Integer specialtyId) {
		List<VetSpecialty> list = vetSpecialtyRepository.findBySpecialtyId(specialtyId);
		list.forEach(item -> log.info("{}", item));
		return list;
	}
}
