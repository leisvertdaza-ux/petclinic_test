package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import com.tecsup.petclinic.repositories.PetTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PetTypeServiceImpl implements PetTypeService {

	private final PetTypeRepository petTypeRepository;

	@Override
	public PetType create(PetType petType) {
		return petTypeRepository.save(petType);
	}

	@Override
	public PetType update(PetType petType) {
		return petTypeRepository.save(petType);
	}

	@Override
	public void delete(Integer id) throws PetTypeNotFoundException {
		PetType petType = findById(id);
		petTypeRepository.delete(petType);
	}

	@Override
	public PetType findById(Integer id) throws PetTypeNotFoundException {
		return petTypeRepository.findById(id)
				.orElseThrow(() -> new PetTypeNotFoundException("Record not found...!"));
	}

	@Override
	public List<PetType> findByName(String name) {
		List<PetType> list = petTypeRepository.findByName(name);
		list.forEach(item -> log.info("{}", item));
		return list;
	}

	@Override
	public List<PetType> findByActive(Boolean active) {
		List<PetType> list = petTypeRepository.findByActive(active);
		list.forEach(item -> log.info("{}", item));
		return list;
	}

	@Override
	public List<PetType> findAll() {
		return petTypeRepository.findAll();
	}
}
