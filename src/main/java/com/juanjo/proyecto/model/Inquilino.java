package com.juanjo.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the inquilino database table.
 * 
 */
@Entity
@NamedQuery(name = "Inquilino.findAll", query = "SELECT i FROM Inquilino i")
public class Inquilino implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private boolean active;

	private String dni;

	@Column(name = "fecha_alta")
	private Timestamp fechaAlta;

	@Column(name = "fecha_nac")
	private Timestamp fechaNac;

	private String firstname;

	private String lastname;

	// bi-directional many-to-one association to Alquiler
	@OneToMany(mappedBy = "inquilino")
	private List<Alquiler> alquilers;

	public Inquilino() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Timestamp getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Timestamp fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Timestamp getFechaNac() {
		return this.fechaNac;
	}

	public void setFechaNac(Timestamp fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<Alquiler> getAlquilers() {
		return this.alquilers;
	}

	public void setAlquilers(List<Alquiler> alquilers) {
		this.alquilers = alquilers;
	}

	public Alquiler addAlquiler(Alquiler alquiler) {
		getAlquilers().add(alquiler);
		alquiler.setInquilino(this);

		return alquiler;
	}

	public Alquiler removeAlquiler(Alquiler alquiler) {
		getAlquilers().remove(alquiler);
		alquiler.setInquilino(null);

		return alquiler;
	}

}