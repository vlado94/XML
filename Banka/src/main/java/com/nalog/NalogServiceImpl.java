package com.nalog;

import java.util.List;

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
	public List<Nalog> findAll() {
		return (List<Nalog>) nalogRepository.findAll();
	}

}
