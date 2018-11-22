package com.apap.tugasakhir.controller;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.apap.tugasakhir.rest.Setting;
import com.apap.tugasakhir.service.RestService;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.*;
import com.apap.tugasakhir.rest.*;

@RestController
@RequestMapping("/dokter")
public class DokterController {
	@Autowired
	RestService restService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}
	

	@RequestMapping()
	public DokterDetail ambilDokter(@RequestParam("id") String id ) throws ParseException{
		int id_dokter = Integer.parseInt(id);
		return restService.getDokter(id_dokter);

	}
	
//	@GetMapping()
//	private Object listModel(@RequestParam("id") String id) throws Exception{
//		System.out.println("coba");
//		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		headers.add("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Mobile Safari/537.36");
//		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
//		String path = Setting.siApp + "/getDokter/" + Long.parseLong(id);
//		Object response = restTemplate.exchange(path, HttpMethod.GET, entity, Object.class);
//		System.out.println(response);
//		return response;
//	}
}