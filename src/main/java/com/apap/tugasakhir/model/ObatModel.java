package com.apap.tugasakhir.model;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "obat")
public class ObatModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Size(max = 255)
	@Column (name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Column (name = "jumlah", nullable = false)
	private int jumlah;
	
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

	public int getJumlah() {
		return jumlah;
	}

	public void setJumlah(int jumlah) {
		this.jumlah = jumlah;
	}

	public List<PenangananModel> getListPenanganan() {
		return listPenanganan;
	}

	public void setListPenanganan(List<PenangananModel> listPenanganan) {
		this.listPenanganan = listPenanganan;
	}

	@OneToMany(mappedBy = "obat", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<PenangananModel> listPenanganan;
}
