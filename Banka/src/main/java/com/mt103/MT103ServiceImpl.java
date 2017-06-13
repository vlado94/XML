package com.mt103;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mt102.MT102Repository;

@Service
@Transactional
public class MT103ServiceImpl implements MT103Service {

	private final MT103Repository MT103Repository;
	
	@Autowired
	public MT103ServiceImpl(final MT103Repository MT103Repository) {
		this.MT103Repository = MT103Repository;
	}

	
	@Override
	public List<MT103> findAll() {
		return (List<MT103>) MT103Repository.findAll();
	}

	@Override
	public MT103 findOne(Long id) {
		return MT103Repository.findOne(id);
	}

}
