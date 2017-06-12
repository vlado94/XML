package com.firma;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class FirmaServiceImpl implements FirmaService {

	private final FirmaRepository firmaRepository;
	
	@Autowired
	public FirmaServiceImpl(final FirmaRepository firmaRepository) {
		this.firmaRepository = firmaRepository;
	}

	@Override
	public List<Firma> findAll() {
		return (List<Firma>) firmaRepository.findAll();
	}

	@Override
	public Firma save(Firma firma) {
		return firmaRepository.save(firma);
	}

	@Override
	public Firma findOne(Long id) {
		return firmaRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		firmaRepository.delete(id);
	}
}
