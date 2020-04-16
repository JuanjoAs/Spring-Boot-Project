package com.juanjo.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the casa database table.
 * 
 */
@Entity
@NamedQuery(name = "Casa.findAll", query = "SELECT c FROM Casa c")
public class Casa implements Serializable {
	@Override
	public String toString() {
		
		return id+". "+nombre+" - "+user.getEmail()+" - "+codVivienda;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private boolean active;

	private String nombre;

	@Column(name = "cod_vivienda")
	private String codVivienda;

	// bi-directional many-to-one association to Alquiler
	@OneToMany(mappedBy = "casa")
	private List<Alquiler> alquilers;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	public Casa() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCodVivienda() {
		return this.codVivienda;
	}

	public void setCodVivienda(String codVivienda) {
		this.codVivienda = codVivienda;
	}

	public List<Alquiler> getAlquilers() {
		return this.alquilers;
	}

	public void setAlquilers(List<Alquiler> alquilers) {
		this.alquilers = alquilers;
	}

	public Alquiler addAlquiler(Alquiler alquiler) {
		getAlquilers().add(alquiler);
		alquiler.setCasa(this);

		return alquiler;
	}

	public Alquiler removeAlquiler(Alquiler alquiler) {
		getAlquilers().remove(alquiler);
		alquiler.setCasa(null);

		return alquiler;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}