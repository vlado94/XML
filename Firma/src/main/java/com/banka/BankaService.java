package com.banka;

import java.util.List;

public interface BankaService {
	public Banka findOne(Long id);

	public List<Banka> findAll();

}
