package com.apap.tugasakhir.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PoliRujukanDetail {
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("nama")
	private String nama;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
}