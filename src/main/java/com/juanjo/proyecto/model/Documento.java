package com.juanjo.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the documento database table.
 * 
 */
@Entity
@NamedQuery(name = "Documento.findAll", query = "SELECT d FROM Documento d")
public class Documento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String ruta;

	// bi-directional one-to-one association to Alquiler
	@OneToOne
	@JoinColumn(name = "id_alquiler")
	private Alquiler alquiler;

	public Documento() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Alquiler getAlquiler() {
		return this.alquiler;
	}

	public void setAlquiler(Alquiler alquiler) {
		this.alquiler = alquiler;
	}

}