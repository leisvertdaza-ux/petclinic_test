package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

	private final SpecialtyRepository specialtyRepository;

	@Override
	public Specialty create(Specialty specialty) {
		return specialtyRepository.save(specialty);
	}

	@Override
	public Specialty update(Specialty specialty) {
		return specialtyRepository.save(specialty);
	}

	@Override
	public void delete(Integer id) throws SpecialtyNotFoundException {
		Specialty specialty = findById(id);
		specialtyRepository.delete(specialty);
	}

	@Override
	public Specialty findById(Integer id) throws SpecialtyNotFoundException {
		return specialtyRepository.findById(id)
				.orElseThrow(() -> new SpecialtyNotFoundException("Record not found...!"));
	}

	@Override
	public List<Specialty> findByName(String name) {
		List<Specialty> list = specialtyRepository.findByName(name);
		list.forEach(item -> log.info("{}", item));
		return list;
	}

	@Override
	public List<Specialty> findByOffice(String office) {
		List<Specialty> list = specialtyRepository.findByOffice(office);
		list.forEach(item -> log.info("{}", item));
		return list;
	}

	@Override
	public List<Specialty> findAll() {
		return specialtyRepository.findAll();
	}
}
