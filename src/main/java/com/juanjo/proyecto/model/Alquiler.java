package com.juanjo.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the alquiler database table.
 * 
 */
@Entity
@NamedQuery(name="Alquiler.findAll", query="SELECT a FROM Alquiler a")
public class Alquiler implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="fecha_entrada")
	private String fechaEntrada;

	@Column(name="fecha_salida")
	private String fechaSalida;

	private float precio;

	//bi-directional many-to-one association to Casa
	@ManyToOne
	@JoinColumn(name="id_casa")
	private Casa casa;

	//bi-directional many-to-many association to Inquilino
	
	@ManyToMany
	private List<Inquilino> inquilinos;

	//bi-directional one-to-one association to Documento
	@OneToOne(mappedBy="alquiler")
	private Documento documento;

	public Alquiler() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFechaEntrada() {
		return this.fechaEntrada;
	}

	/*
	 * public Timestamp getDate(String x) { try { SimpleDateFormat dateFormat = new
	 * SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS"); Date parsedDate =
	 * dateFormat.parse(x); System.out.println("la fecha es:"+new
	 * Timestamp(parsedDate.getTime()).toString()); return new
	 * Timestamp(parsedDate.getTime()); } catch(Exception e) { //this generic but
	 * you can control another types of exception // look the origin of excption }
	 * return fechaEntrada; }
	 */
	public void setFechaEntrada(String fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public String getFechaSalida() {
		return this.fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public float getPrecio() {
		return this.precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Casa getCasa() {
		return this.casa;
	}

	public void setCasa(Casa casa) {
		this.casa = casa;
	}

	public List<Inquilino> getInquilinos() {
		return this.inquilinos;
	}

	public void setInquilinos(List<Inquilino> inquilinos) {
		this.inquilinos = inquilinos;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	@Override
	public String toString() {
		return "Hay un alquiler to guapo con id="+id+" y ademas tiene el id de casa="+this.casa.getId();
	}

}