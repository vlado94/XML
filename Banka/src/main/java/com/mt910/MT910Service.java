package com.mt910;

import java.util.List;

public interface MT910Service {


	public List<MT910> findAll();

	public MT910 save(MT910 mt102);

	public MT910 findOne(Long id);
	
	public MT910 findByidPorukeNaloga(String idPorukeNaloga);
}
