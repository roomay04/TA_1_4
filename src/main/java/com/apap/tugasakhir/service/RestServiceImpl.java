package com.apap.tugasakhir.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.apap.tugasakhir.rest.PasienDetail;
import com.apap.tugasakhir.rest.Setting;
import com.apap.tugasakhir.rest.StatusPasienDetail;

@Service
public class RestServiceImpl implements RestService{
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public String getRest(String url) throws ParseException{
		String response = restTemplate.getForObject(url, String.class);
        return response;
	}
	
	@Override
	public PasienDetail parsePasien(String data) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(data);
		JSONObject pasienJson = (JSONObject) json.get("result");
		System.out.println(pasienJson);
		JSONObject statusPasien = (JSONObject) pasienJson.get("statusPasien");
		StatusPasienDetail status = new StatusPasienDetail();
		status.setId((int) (long) statusPasien.get("id"));
		status.setJenis((String) statusPasien.get("jenis"));
		PasienDetail pasien = new PasienDetail();
		pasien.setId((int) (long) pasienJson.get("id"));
		pasien.setNama((String) pasienJson.get("nama"));
		pasien.setStatusPasien(status);
		System.out.println(pasien.getNama());
		return pasien;
	}
	
	@Override
	public List<PasienDetail> parseAllPasien(String data) throws ParseException{
		List<PasienDetail> allPasien = new ArrayList<PasienDetail>();
		
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(data);
		System.out.println("BISA");
		JSONArray res = (JSONArray) json.get("result");
		System.out.println(res);
		
		Iterator i = res.iterator();
		
		System.out.println("characters: "); 
		while (i.hasNext()) { 
			JSONObject pasienJson = (JSONObject) i.next();
			JSONObject statusPasien = (JSONObject) pasienJson.get("statusPasien");
			StatusPasienDetail status = new StatusPasienDetail();
			status.setId((int) (long) statusPasien.get("id"));
			status.setJenis((String) statusPasien.get("jenis"));
			PasienDetail pasien = new PasienDetail();
			pasien.setId((int) (long) pasienJson.get("id"));
			pasien.setNama((String) pasienJson.get("nama"));
			pasien.setStatusPasien(status);
			allPasien.add(pasien);
			System.out.println(pasien.getNama());
		}
		return allPasien;
	}
}