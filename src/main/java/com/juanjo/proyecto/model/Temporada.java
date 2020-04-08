package com.juanjo.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the temporada database table.
 * 
 */
@Entity
@NamedQuery(name = "Temporada.findAll", query = "SELECT t FROM Temporada t")
public class Temporada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private float bonus;

	private String nombre;

	public Temporada() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getBonus() {
		return this.bonus;
	}

	public void setBonus(float bonus) {
		this.bonus = bonus;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}