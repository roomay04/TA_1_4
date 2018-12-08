package com.apap.tugasakhir.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.apap.tugasakhir.model.JadwalPoliModel;
import com.apap.tugasakhir.model.PenangananModel;
import com.apap.tugasakhir.model.RujukanRawatJalanModel;
import com.apap.tugasakhir.repository.JadwalPoliDb;
import com.apap.tugasakhir.repository.PoliDb;
import com.apap.tugasakhir.repository.RujukanRawatJalanDb;
import com.apap.tugasakhir.rest.PasienRujukanDetail;
import com.apap.tugasakhir.rest.Setting;
import com.apap.tugasakhir.rest.StatusPasienDetail;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
@Transactional

public class RujukanServiceImpl implements RujukanService {
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	RestService restService;

	@Autowired
	private RujukanRawatJalanDb rujukanDb;

	@Autowired
	private JadwalPoliDb jadwalPoliDb;
	
	@Autowired
	private PoliDb poliDb;

	@Override
	public RujukanRawatJalanModel getRujukanById(Long id) {
		// TODO Auto-generated method stub
		return rujukanDb.getOne(id);
	}

	@Override
	public void changeRujukan(PasienRujukanDetail pasien, String status) throws ParseException {
		int idStatusNow = 0;
		RujukanRawatJalanModel pasienRujuk = rujukanDb.findByIdPasien((int) pasien.getId()).get();
		if (status.equalsIgnoreCase("mendaftar poli"))
			idStatusNow = 1;
		else if (status.equalsIgnoreCase("berada di poli"))
			idStatusNow = 2;
		else if (status.equalsIgnoreCase("selesai"))
			idStatusNow = 3;
		if (pasienRujuk.getStatus() < idStatusNow && idStatusNow - pasienRujuk.getStatus() == 1) {
//			pasien.getStatusPasien().setId(idStatusNow);
//			pasien.getStatusPasien().setJenis(status);
			pasienRujuk.setStatus(idStatusNow);
			//manggil API dari siAppointment untuk update status
			//restService.updateStatusPasien(pasien);
			System.out.println("masuk update status berhasil");
			System.out.println(pasienRujuk.getStatus());
		}
		if (idStatusNow == 3) {
			pasien.setStatusPasien(new StatusPasienDetail(9, "Selesai di Rawat Jalan"));
			restService.updateStatusPasien(pasien);
		}
	}

	@Override
	public void validateRujukan(PasienRujukanDetail pasien) {
		if (!rujukanDb.findByIdPasien(pasien.getId()).isPresent()) {
			// get jadwal terdekat
			System.out.println(pasien.getPoliRujukan().getId());
			
			List<JadwalPoliModel> results = jadwalPoliDb.findByPoliIdAndTanggalGreaterThanEqualOrderByTanggalDesc(pasien.getPoliRujukan().getId(), pasien.getTanggalRujukan());
			if (results.size() > 0 && pasien.getPoliRujukan()!=null) {
				RujukanRawatJalanModel rujukan = new RujukanRawatJalanModel();
				rujukan.setIdPasien(pasien.getId());
				rujukan.setJadwalPoli(results.get(0));
				rujukan.setNamaPasien(pasien.getNama());
				rujukan.setStatus(1);
				System.out.println("masuk update status");
				rujukan.setListPenanganan(new ArrayList<PenangananModel>());
				rujukan.setTanggalRujuk(pasien.getTanggalRujukan());
				rujukanDb.save(rujukan);
				System.out.println("Tanggal rujukan - tanggal terdekat");
				System.out.println(pasien.getId());
				System.out.println(pasien.getTanggalRujukan());
				System.out.println(results.get(0).getTanggal());
			} else {
				System.out.println("ga ada yg terdekat");
				System.out.println(pasien.getTanggalRujukan());
			}
		}
	}
	
	@Override
	public List<PasienRujukanDetail> getAllPasienRujukan() throws ParseException, JsonParseException, JsonMappingException, IOException {
		// get from SiAppointment
				String urlApp = Setting.siApp+"/4/getAllPasienRawatJalan/";
				String responseApp = restService.getRest(urlApp);
				ArrayList<PasienRujukanDetail> listPasien = (ArrayList<PasienRujukanDetail>) restService.parseListPasien(responseApp);
				
				// get from SiIGD
				String urlIGD = Setting.siIGD+"/rujukan";
				String responseIGD = restService.getRest(urlIGD);
				ArrayList<PasienRujukanDetail> listPasienIGD = (ArrayList<PasienRujukanDetail>) restService.parsePasienIGD(responseIGD);
				listPasien.addAll(listPasienIGD);
				for (PasienRujukanDetail pasien : listPasien) {
					validateRujukan(pasien);
				}
		return listPasien;
	}
	
	@Override
	public ArrayList<Object> getAllRujukan() throws ParseException, JsonParseException, JsonMappingException, IOException{
		List<RujukanRawatJalanModel> allRujukan = rujukanDb.findAll();
		System.out.println("sizenya "+allRujukan.size());
		ArrayList<Object> output = new ArrayList<Object>();
		HashMap<Integer, RujukanRawatJalanModel> mapRujukan = new HashMap<Integer, RujukanRawatJalanModel>();
		
		String urlApp = Setting.siApp+"/getPasien?listId=";
		
		for (RujukanRawatJalanModel rujukan : allRujukan) {
			mapRujukan.put(rujukan.getIdPasien(), rujukan);
			urlApp = urlApp.concat(rujukan.getIdPasien()+",");
			System.out.println(urlApp);
		}
		urlApp = urlApp.substring(0, urlApp.length()-1).concat("&resultType=List");
		System.out.println(urlApp);
		String responseApp = restService.getRest(urlApp);
		System.out.println(responseApp);
		ArrayList<PasienRujukanDetail> listPasien = (ArrayList<PasienRujukanDetail>) restService.parseListPasien(responseApp);
		output.add(mapRujukan);
		output.add(listPasien);
		return output;
	}
}
