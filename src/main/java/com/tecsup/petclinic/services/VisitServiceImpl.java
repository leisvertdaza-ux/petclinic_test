package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.repositories.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

	private final VisitRepository visitRepository;

	@Override
	public Visit create(Visit visit) {
		return visitRepository.save(visit);
	}

	@Override
	public Visit update(Visit visit) {
		return visitRepository.save(visit);
	}

	@Override
	public void delete(Integer id) throws VisitNotFoundException {
		Visit visit = findById(id);
		visitRepository.delete(visit);
	}

	@Override
	public Visit findById(Integer id) throws VisitNotFoundException {
		return visitRepository.findById(id)
				.orElseThrow(() -> new VisitNotFoundException("Record not found...!"));
	}

	@Override
	public List<Visit> findByPetId(Integer petId) {
		List<Visit> list = visitRepository.findByPetId(petId);
		list.forEach(item -> log.info("{}", item));
		return list;
	}

	@Override
	public List<Visit> findByVetId(Integer vetId) {
		List<Visit> list = visitRepository.findByVetId(vetId);
		list.forEach(item -> log.info("{}", item));
		return list;
	}

	@Override
	public List<Visit> findAll() {
		return visitRepository.findAll();
	}
}
