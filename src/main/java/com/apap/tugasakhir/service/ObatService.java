package com.apap.tugasakhir.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugasakhir.model.ObatModel;

public interface ObatService {

	public Optional<ObatModel> findByNama(String nama);
	public void save(ObatModel obat);
	List<ObatModel> getListObat();
}
