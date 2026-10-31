package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

	private final OwnerRepository ownerRepository;

	@Override
	public Owner create(Owner owner) {
		return ownerRepository.save(owner);
	}

	@Override
	public Owner update(Owner owner) {
		return ownerRepository.save(owner);
	}

	@Override
	public void delete(Integer id) throws OwnerNotFoundException {
		Owner owner = findById(id);
		ownerRepository.delete(owner);
	}

	@Override
	public Owner findById(Integer id) throws OwnerNotFoundException {
		return ownerRepository.findById(id)
				.orElseThrow(() -> new OwnerNotFoundException("Record not found...!"));
	}

	@Override
	public List<Owner> findByLastName(String lastName) {
		List<Owner> list = ownerRepository.findByLastName(lastName);
		list.forEach(item -> log.info("{}", item));
		return list;
	}

	@Override
	public List<Owner> findByCity(String city) {
		List<Owner> list = ownerRepository.findByCity(city);
		list.forEach(item -> log.info("{}", item));
		return list;
	}

	@Override
	public List<Owner> findAll() {
		return ownerRepository.findAll();
	}
}
