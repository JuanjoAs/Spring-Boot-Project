package com.juanjo.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the alquiler database table.
 * 
 */
@Entity
@NamedQuery(name = "Alquiler.findAll", query = "SELECT a FROM Alquiler a")
public class Alquiler implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "cod_casa")
	private String codCasa;

	@Column(name = "fecha_entrada")
	private Timestamp fechaEntrada;

	@Column(name = "fecha_salida")
	private Timestamp fechaSalida;

	private float precioBase;

	private float precioFinal;

	// bi-directional many-to-one association to Casa
	@ManyToOne
	@JoinColumn(name = "id_casa")
	private Casa casa;

	// bi-directional many-to-one association to Inquilino
	@ManyToOne
	@JoinColumn(name = "id_inquilino")
	private Inquilino inquilino;

	// bi-directional one-to-one association to Documento
	@OneToOne(mappedBy = "alquiler")
	private Documento documento;

	public Alquiler() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodCasa() {
		return this.codCasa;
	}

	public void setCodCasa(String codCasa) {
		this.codCasa = codCasa;
	}

	public Timestamp getFechaEntrada() {
		return this.fechaEntrada;
	}

	public void setFechaEntrada(Timestamp fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public Timestamp getFechaSalida() {
		return this.fechaSalida;
	}

	public void setFechaSalida(Timestamp fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public float getPrecioBase() {
		return this.precioBase;
	}

	public void setPrecioBase(float precioBase) {
		this.precioBase = precioBase;
	}

	public float getPrecioFinal() {
		return this.precioFinal;
	}

	public void setPrecioFinal(float precioFinal) {
		this.precioFinal = precioFinal;
	}

	public Casa getCasa() {
		return this.casa;
	}

	public void setCasa(Casa casa) {
		this.casa = casa;
	}

	public Inquilino getInquilino() {
		return this.inquilino;
	}

	public void setInquilino(Inquilino inquilino) {
		this.inquilino = inquilino;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

}