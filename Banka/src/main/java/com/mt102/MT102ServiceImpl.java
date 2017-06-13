package com.mt102;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MT102ServiceImpl implements MT102Service {


	private final MT102Repository MT102Repository;
	//private final ZaglavljeMT102Repository zaglavljeMT102Repository;
	//private final PojedinacnoPlacanjeMT102Repository pojedinacnoPlacanjeMT102Repository;
	
	@Autowired
	public MT102ServiceImpl(final MT102Repository MT102Repository/*, final ZaglavljeMT102Repository zaglavljeMT102Repository, final PojedinacnoPlacanjeMT102Repository pojedinacnoPlacanjeMT102Repository*/) {
		this.MT102Repository = MT102Repository;
		//this.zaglavljeMT102Repository = zaglavljeMT102Repository;
		//this.pojedinacnoPlacanjeMT102Repository = pojedinacnoPlacanjeMT102Repository;
	}

	@Override
	public List<MT102> findAll() {
		return (List<MT102>) MT102Repository.findAll();
	}

	/*@Override
	public MT102 save(MT102 mt102) {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public MT102 findOne(Long id) {
		return MT102Repository.findOne(id);
	}
}
