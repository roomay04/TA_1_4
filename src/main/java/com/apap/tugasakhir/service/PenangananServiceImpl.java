package com.apap.tugasakhir.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugasakhir.model.PenangananModel;
import com.apap.tugasakhir.repository.PenangananDb;


@Service
@Transactional
public class PenangananServiceImpl implements PenangananService {
	@Autowired
	private PenangananDb penangananDb;
	
	@Override
	public void addPenanganan(PenangananModel penanganan) {
		// TODO Auto-generated method stub
		penangananDb.save(penanganan);
	}

}