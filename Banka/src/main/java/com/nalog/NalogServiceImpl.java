package com.nalog;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NalogServiceImpl implements NalogService {

	private final NalogRepository nalogRepository;

	@Autowired
	public NalogServiceImpl(final NalogRepository nalogRepository) {
		this.nalogRepository = nalogRepository;
	}

	@Override
	public Nalog findOne(Long id) {
		return nalogRepository.findOne(id);
	}

	@Override
	public Nalog save(Nalog nalog) {
		return nalogRepository.save(nalog);
	}

}
