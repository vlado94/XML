package com.banka;

import org.springframework.data.jpa.repository.Query;

public interface BankaService {
	public Banka findOne(Long id);

	public Banka findByKod(String kod);
}
