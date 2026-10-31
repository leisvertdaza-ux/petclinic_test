package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

	private final VetRepository vetRepository;

	@Override
	public Vet create(Vet vet) {
		return vetRepository.save(vet);
	}

	@Override
	public Vet update(Vet vet) {
		return vetRepository.save(vet);
	}

	@Override
	public void delete(Integer id) throws VetNotFoundException {
		Vet vet = findById(id);
		vetRepository.delete(vet);
	}

	@Override
	public Vet findById(Integer id) throws VetNotFoundException {
		return vetRepository.findById(id)
				.orElseThrow(() -> new VetNotFoundException("Record not found...!"));
	}

	@Override
	public List<Vet> findByLastName(String lastName) {
		List<Vet> list = vetRepository.findByLastName(lastName);
		list.forEach(item -> log.info("{}", item));
		return list;
	}

	@Override
	public List<Vet> findByActive(Boolean active) {
		List<Vet> list = vetRepository.findByActive(active);
		list.forEach(item -> log.info("{}", item));
		return list;
	}

	@Override
	public List<Vet> findAll() {
		return vetRepository.findAll();
	}
}
