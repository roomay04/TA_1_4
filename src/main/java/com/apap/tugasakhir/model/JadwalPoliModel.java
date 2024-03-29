package com.apap.tugasakhir.model;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "jadwal_poli")
public class JadwalPoliModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Column (name = "tanggal", nullable = false)
	private Date tanggal;
	
	@NotNull
	@Column (name = "jam_mulai", nullable = false)
	private String jamMulai;
	
	@NotNull
	@Column (name = "jam_selesai", nullable = false)
	private String jamSelesai;
	
	@NotNull
	@Column (name = "id_dokter", nullable = false)
	private int idDokter;
	
	public int getIdDokter() {
		return idDokter;
	}

	public void setIdDokter(int idDokter) {
		this.idDokter = idDokter;
	}

	public List<RujukanRawatJalanModel> getListRujukan() {
		return listRujukan;
	}

	public void setListRujukan(List<RujukanRawatJalanModel> listRujukan) {
		this.listRujukan = listRujukan;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_poli", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private PoliModel poli;
	
	@OneToMany(mappedBy = "jadwalPoli", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<RujukanRawatJalanModel> listRujukan;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	

	public String getJamMulai() {
		return jamMulai;
	}

	public void setJamMulai(String jamMulai) {
		this.jamMulai = jamMulai;
	}

	public String getJamSelesai() {
		return jamSelesai;
	}

	public void setJamSelesai(String jamSelesai) {
		this.jamSelesai = jamSelesai;
	}

	public PoliModel getPoli() {
		return poli;
	}

	public void setPoli(PoliModel poli) {
		this.poli = poli;
	}
	
}
